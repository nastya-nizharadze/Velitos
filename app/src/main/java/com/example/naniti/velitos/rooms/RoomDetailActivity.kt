package com.example.naniti.velitos.rooms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.RoomsSearch
import kotlinx.android.synthetic.main.activity_room_detail.*

class RoomDetailActivity : AppCompatActivity() {


        lateinit var rooms_detail: RoomsSearch

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_room_detail)

            val intent = intent

            room_name.text = intent.getStringExtra("name")
            room_category.text = intent.getStringExtra("category")
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
