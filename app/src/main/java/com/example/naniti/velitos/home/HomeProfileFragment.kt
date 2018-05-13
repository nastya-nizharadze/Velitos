package com.example.naniti.velitos.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naniti.velitos.MainActivity
import com.example.naniti.velitos.R
import com.example.naniti.velitos.challenges.ChallengeAdapter
import com.example.naniti.velitos.internet.UsersSearch

import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.android.synthetic.main.fragment_home_profile.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class HomeProfileFragment : Fragment() {

    lateinit var httpClient: LeningradskayaClient

    companion object {
        fun newInstance() = HomeProfileFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val profile = inflater.inflate(R.layout.fragment_home_profile, container, false)
        httpClient = (activity as MainActivity).httpClient

        async(UI) {
            val activity = getActivity()
            //Никитв вставь
            person_name.text = "Vasya Pupkin"
            tv_item1.text = "Санкт-Петербург"
            tv_item.text = "21 марта 1998"

        }
        return profile
    }


}
