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
import com.example.naniti.velitos.internet.ChallengesSearch

//import com.example.naniti.velitos.internet.ProfileSearch
import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.android.synthetic.main.fragment_home_profile.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class HomeProfileFragment : Fragment() {

    lateinit var httpClient: LeningradskayaClient
   // var profilesDetail: ArrayList<ProfileSearch> = ArrayList()

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
            //challengesDetail = httpClient.getProfileSearch().await()!!.toCollection(ArrayList())
            //rv_profile_list.layoutManager = LinearLayoutManager(activity)
            //rv_profile_list.adapter = ChallengeAdapter(
            //        profileDetail, activity,
            //        { user: ProfileSearch -> partItemClicked(user) })
            //person_name.text = user.name!!
            person_name.text = "Vasya Pupkin"
        }
        return profile
    }


}
