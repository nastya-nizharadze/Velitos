package com.example.naniti.velitos.baseclasses

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.naniti.velitos.R


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {

    private var mSnackBar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(
                ConnectionReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun showMessage(isConnected: Boolean) {
        if (isConnected) {
            mSnackBar?.dismiss()
        } else {
            val message = "You are offline now"
            mSnackBar = Snackbar.make(
                    findViewById(R.id.rootLayout),
                    message,
                    Snackbar.LENGTH_LONG
            )
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectionReceiver.connectionReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}