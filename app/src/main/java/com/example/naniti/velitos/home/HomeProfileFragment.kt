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
import com.example.naniti.velitos.R.drawable.noavatar_profile
import com.example.naniti.velitos.internet.LeningradskayaClient
import com.squareup.picasso.Picasso
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

        val pref = activity.getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)

            async(UI) {

                val personBirthDay = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.birthDate
                if (personBirthDay != null) {
                    tv_item.text = personBirthDay.toString()
                } else {
                    tv_item.text = "Дата рождения не указана"
                }
                val personGender = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.gender
                if (personGender != null) {
                    tv_item1.text = personGender
                } else {
                    tv_item1.text = "Пол не указан"
                }
                val personBiography = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.bio
                if (personBiography != null) {
                    tv_item5.text = personBiography
                } else {
                    tv_item5.text = "Биография не указана"
                }

                val personPopularity = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.popularity
                tv_item8.text = personPopularity.toString()

                val personFirstName = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.user!!.firstName
                val personLastName = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.user!!.lastName
                if (personFirstName != "" && personLastName != "") {
                    person_name.text = "$personFirstName $personLastName"
                } else {
                    person_name.text = "Имя и фамилия не указаны"
                }

                val personImage = httpClient.getUserProfileInstance(pref.getString("USERNAME", "")).await()!!.image.toString()
                if (personImage != "") {
                    Picasso.get().load(personImage).into(imageView)
                } else {
                    imageView.setImageResource(noavatar_profile)
                }
            }
        return profile
    }

}
