package com.example.naniti.velitos.challenges

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.ChallengesSearch
import android.content.Intent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_challenge_detail.*


class ChallengeDetailActivity : AppCompatActivity() {

    lateinit var challenge_detail: ChallengesSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_detail)

        val intent = intent

        challenge_name.text = intent.getStringExtra("name")
        //challenge_creator.text = intent.getStringExtra("challengeCreator")
        challenge_cost.text = intent.getStringExtra("cost")
        challenge_description.text = intent.getStringExtra("description")
        challenge_difficulty.text = intent.getStringExtra("difficulty")
        //challenge_editDate.text = intent.getStringExtra("editDate")
        challenge_popularity.text = intent.getStringExtra("popularity")
        //challenge_pubDate.text = intent.getStringExtra("pubDate")
        val button = button1
        button.setOnClickListener(clickListener)
    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button1 -> {
                Toast.makeText(this@ChallengeDetailActivity, "button pressed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
