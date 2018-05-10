package com.example.naniti.velitos.rooms


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R


class RoomsMyRooms : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onRefresh() {
        refreshLayout.isRefreshing = true;

    }


    companion object {
        fun newInstance() = RoomsMyRooms()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_rooms_my_rooms, container, false)

        refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_rooms_my_rooms);


        return view
    }


}
