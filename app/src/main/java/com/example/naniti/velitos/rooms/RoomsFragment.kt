package com.example.naniti.velitos.rooms

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R
import com.example.naniti.velitos.baseclasses.TabViewPagerAdapter
import com.example.naniti.velitos.rooms.availableRooms.RoomsAvailableRooms
import com.example.naniti.velitos.rooms.myRooms.RoomsMyRooms


class RoomsFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var firstViewPager: ViewPager


    companion object {
        val Tag = RoomsFragment::class.java.simpleName
        fun newInstance() = RoomsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        firstViewPager = rootView.findViewById(R.id.viewpager_content)

        tabLayout = rootView.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(firstViewPager)
        setupViewPager(firstViewPager)
        return rootView
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = TabViewPagerAdapter(childFragmentManager)
        adapter.addFragment(RoomsMyRooms.newInstance(), "My rooms")
        adapter.addFragment(RoomsAvailableRooms.newInstance(), "Available")
        viewPager.adapter = adapter
    }

}

