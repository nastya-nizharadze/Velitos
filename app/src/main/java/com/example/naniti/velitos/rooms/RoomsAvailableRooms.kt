package com.example.naniti.velitos.rooms


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R

class RoomsAvailableRooms : Fragment() {

    companion object {
        fun newInstance() = RoomsAvailableRooms()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms_available_rooms, container, false)
    }


}
