package com.example.naniti.velitos.challenges


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.naniti.velitos.MainActivity

import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.ChallengesSearch
import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.android.synthetic.main.fragment_challenges_new.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class ChallengesNewFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        async(UI) {
            loadData()
        }
    }

    lateinit var httpClient: LeningradskayaClient
    var challengesDetail: ArrayList<ChallengesSearch> = ArrayList()
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    companion object {
        fun newInstance() = ChallengesNewFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_challenges_new, container, false)
        httpClient = (activity as MainActivity).httpClient


        // SwipeRefreshLayout
        mSwipeRefreshLayout = view.findViewById(R.id.challenges_new_swipe_container)
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
        challengesDetail = httpClient.getChallengesSearch().await()!!.toCollection(ArrayList())
        rv_challenge_list.layoutManager = LinearLayoutManager(activity)
        rv_challenge_list.adapter = ChallengeAdapter(
                challengesDetail, activity,
                { challenge: ChallengesSearch -> partItemClicked(challenge) })
        mSwipeRefreshLayout.isRefreshing = false
    }


    private fun partItemClicked(challengesSearch: ChallengesSearch) {
        Toast.makeText(activity, "Clicked: ${challengesSearch.name}", Toast.LENGTH_LONG).show()

        val showDetailActivityIntent = Intent(activity, ChallengeDetailActivity::class.java)
        //bad code below
        showDetailActivityIntent.putExtra("name", challengesSearch.name.toString())
        showDetailActivityIntent.putExtra("challengeCreator", challengesSearch.challengeCreator.toString())
        showDetailActivityIntent.putExtra("cost", challengesSearch.cost.toString())
        showDetailActivityIntent.putExtra("description", challengesSearch.description.toString())
        showDetailActivityIntent.putExtra("difficulty", challengesSearch.difficulty.toString())
        showDetailActivityIntent.putExtra("editDate", challengesSearch.editDate.toString())
        showDetailActivityIntent.putExtra("popularity", challengesSearch.popularity.toString())
        showDetailActivityIntent.putExtra("pubDate", challengesSearch.pubDate.toString())
        startActivity(showDetailActivityIntent)

    }

}




