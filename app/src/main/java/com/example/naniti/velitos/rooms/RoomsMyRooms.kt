package com.example.naniti.velitos.rooms


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R


class RoomsMyRooms : Fragment() {

    companion object {
        fun newInstance() = RoomsMyRooms()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_rooms_my_rooms, container, false)
    }


}
