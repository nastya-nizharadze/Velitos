package com.example.naniti.velitos.home


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naniti.velitos.MainActivity

import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.android.synthetic.main.fragment_home_profile.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class HomeAchievementsFragment : Fragment() {

    lateinit var httpClient: LeningradskayaClient

    companion object {
        fun newInstance() = HomeAchievementsFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val achiv = inflater.inflate(R.layout.fragment_home_achievements, container, false)
        httpClient = (activity as MainActivity).httpClient

        val pref = activity.getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)

        async(UI) {

            val personRating = httpClient.getProfileInstance(pref.getString("USERNAME", "")).await()!!.globalRating
            tv_item.text = ("$personRating points")
        }

        return achiv
    }

}
