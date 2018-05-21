package com.example.naniti.velitos.rooms.availableRooms


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.naniti.velitos.MainActivity

import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.LeningradskayaClient
import com.example.naniti.velitos.internet.RoomsSearch
import com.example.naniti.velitos.rooms.detailRoom.RoomDetailActivity
import kotlinx.android.synthetic.main.fragment_rooms_available_rooms.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class RoomsAvailableRooms : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        async(UI) {
            loadData()
        }
    }

    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    lateinit var httpClient: LeningradskayaClient
    var roomsDetail: ArrayList<RoomsSearch> = ArrayList()


    companion object {
        fun newInstance() = RoomsAvailableRooms()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rooms_available_rooms, container, false)
        httpClient = (activity as MainActivity).httpClient


        // SwipeRefreshLayout
        mSwipeRefreshLayout = view.findViewById(R.id.rooms_available_swipe_container)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        async(UI) {
            loadData()
        }
        return view
    }

    private suspend fun loadData() {
        mSwipeRefreshLayout.isRefreshing = true
        roomsDetail = httpClient.getRoomsSearch().await()!!.toCollection(ArrayList())
        rv_room_list.layoutManager = LinearLayoutManager(activity)
        rv_room_list.adapter = RoomAdapter(
                roomsDetail, activity,
                { room: RoomsSearch -> partItemClicked(room) })
        mSwipeRefreshLayout.isRefreshing = false
    }

    private fun partItemClicked(roomsSearch: RoomsSearch) {
        Toast.makeText(activity, "Clicked: ${roomsSearch.name}", Toast.LENGTH_LONG).show()
        val showDetailActivityIntent = Intent(activity, RoomDetailActivity::class.java)
        showDetailActivityIntent.putExtra("name", roomsSearch.name.toString())
        showDetailActivityIntent.putExtra("category", roomsSearch.category.toString())
        showDetailActivityIntent.putExtra("url", roomsSearch.url.toString())
        startActivity(showDetailActivityIntent)

    }


}
