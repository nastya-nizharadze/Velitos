package com.example.naniti.velitos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.naniti.velitos.signuplogin.LoginActivity


/*
Short description:
  Activity for check that user logged in
  Get shared preferences(token)
  connect to server and validate token
  If all ok then move to main activity
  Else move to Login Activity
  Need to check internet connection
  NEED SPINNER
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)

        if (pref.getString("JWTTOKEN", "") != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}

