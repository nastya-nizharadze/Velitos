package com.example.naniti.velitos.challenges


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R


class ChallengesPopularFragment : Fragment() {

    companion object {
        fun newInstance() = ChallengesPopularFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges_popular, container, false)
    }


}