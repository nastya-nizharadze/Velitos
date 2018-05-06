package com.example.naniti.velitos.challenges


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.naniti.velitos.R

class ChallengesInterestingFragment : Fragment() {

    companion object {
        fun newInstance() = ChallengesInterestingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges_intresting, container, false)
    }


}
