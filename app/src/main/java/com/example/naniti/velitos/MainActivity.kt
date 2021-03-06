package com.example.naniti.velitos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import com.example.naniti.velitos.baseclasses.BaseActivity
import com.example.naniti.velitos.fragments.ui.helper.BottomNavigationPosition
import com.example.naniti.velitos.fragments.ui.helper.findNavigationPositionById
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu

import android.view.MenuItem
import com.example.naniti.velitos.fragments.ui.helper.createFragment
import com.example.naniti.velitos.fragments.ui.helper.getTag
import android.view.MenuInflater
import com.example.naniti.velitos.internet.LeningradskayaClient
import com.example.naniti.velitos.internet.websocket.LeningradskayaWebSocketApp
import com.example.naniti.velitos.signuplogin.LoginActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    val httpClient = LeningradskayaClient("http://hserver.leningradskaya105.ru:6379")
    lateinit var username: String
    private val KEY_POSITION = "keyPosition"
    lateinit var socket: LeningradskayaWebSocketApp

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME

    private lateinit var toolbar: Toolbar

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.search)
        // Detect SearchView icon clicks
        // Detect SearchView close
        searchItem.setOnActionExpandListener(
                object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(search: MenuItem?): Boolean {
                        setItemsVisibility(menu, search!!, false)
                        return true
                    }

                    override fun onMenuItemActionCollapse(search: MenuItem?): Boolean {
                        setItemsVisibility(menu, search!!, true)
                        return true
                    }
                }
        )


        return super.onCreateOptionsMenu(menu)

    }


    private fun setItemsVisibility(menu: Menu, exception: MenuItem, visible: Boolean) {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item != exception) item.isVisible = visible

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        // this.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        socket = LeningradskayaWebSocketApp.applicationContext() as LeningradskayaWebSocketApp

        restoreSaveInstanceState(savedInstanceState)
        setContentView(R.layout.activity_main)
        launch(UI) {
            bg {
                socket.openSocketConnection()

            }.await()
        }

        val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        username = pref.getString("USERNAME", "")
        if (pref.getString("JWTTOKEN", "") != "") {
            httpClient.clientToken = pref.getString("JWTTOKEN", "")
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        toolbar = findViewById(R.id.toolbar)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        setSupportActionBar(toolbar)
        initBottomNavigation()
        initFragment(savedInstanceState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navPosition = findNavigationPositionById(item.itemId)
        return switchFragment(navPosition)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        // Store the current navigation position.
        outState?.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }


    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.also {
            val id = it.getInt(KEY_POSITION, BottomNavigationPosition.HOME.id)
            navPosition = findNavigationPositionById(id)
        }
    }


    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(BottomNavigationPosition.HOME)
    }

    /**
     * Immediately execute transactions with FragmentManager#executePendingTransactions.
     */
    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        val fragment = supportFragmentManager.findFragment(navPosition)
        if (fragment.isAdded) return false
        detachFragment()
        attachFragment(fragment, navPosition.getTag())
        supportFragmentManager.executePendingTransactions()
        return true
    }

    private fun FragmentManager.findFragment(position: BottomNavigationPosition): Fragment {
        return findFragmentByTag(position.getTag()) ?: position.createFragment()
    }

    private fun detachFragment() {
        supportFragmentManager.findFragmentById(R.id.container)?.also {
            supportFragmentManager.beginTransaction().detach(it).commit()
        }
    }

    private fun attachFragment(fragment: Fragment, tag: String) {
        if (fragment.isDetached) {
            supportFragmentManager.beginTransaction().attach(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment, tag).commit()
        }
        // Set a transition animation for this transaction.
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

}
