package com.example.naniti.velitos.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R


class HomeAchievementsFragment : Fragment() {




    companion object {
        fun newInstance() = HomeAchievementsFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_achievements, container, false)
    }


}
