package com.example.naniti.velitos.rooms

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.android.synthetic.main.activity_room_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class RoomDetailActivity : AppCompatActivity() {

        lateinit var httpClient: LeningradskayaClient

        companion object {
            fun newInstance() = RoomDetailActivity()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_room_detail)

            httpClient = LeningradskayaClient("http://hserver.leningradskaya105.ru:6379")

            val intent = intent
            httpClient.clientToken=getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE).getString("JWTTOKEN", "")
            room_name.text = intent.getStringExtra("name")
            async(UI) {

                val nameCategory = httpClient.getRoomCategoryInstance(intent.getStringExtra("category")).await()!!.name
                if (nameCategory!= null) {
                    room_category.text = nameCategory
                }
                else room_category.text = "У этого челленджа нет категории"

            }
            val button = button1
            button.setOnClickListener(clickListener)
        }

        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.button1 -> {
                    Toast.makeText(this@RoomDetailActivity, "button pressed", Toast.LENGTH_SHORT).show()
                }
            }
        }
}
