package com.example.naniti.velitos.rooms.detailRoom

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.LeningradskayaClient
import com.example.naniti.velitos.internet.MessageSend
import com.example.naniti.velitos.internet.websocket.LeningradskayaWebSocketApp
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_room_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class RoomDetailActivity : AppCompatActivity() {

    lateinit var httpClient: LeningradskayaClient
    lateinit var socket: LeningradskayaWebSocketApp
    lateinit var roomLabel: String

    companion object {
        fun newInstance() = RoomDetailActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_detail)

        httpClient = LeningradskayaClient("http://hserver.leningradskaya105.ru:6379")

        val intent = intent
        httpClient.clientToken = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE).getString("JWTTOKEN", "")
        roomLabel = intent.getStringExtra("name")//HARDCODED
        room_name.text = intent.getStringExtra("name")
        async(UI) {

            val nameCategory = httpClient.getRoomCategoryInstance(intent.getStringExtra("category")).await()!!.name
            if (nameCategory != null) {
                room_category.text = nameCategory
            } else room_category.text = "У этого челленджа нет категории"

        }
        val button = button1
        button.setOnClickListener(clickListener)
    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button1 -> {
                socket = LeningradskayaWebSocketApp.applicationContext() as LeningradskayaWebSocketApp
                async(UI) {
                    val json = Gson().toJson(MessageSend("join", "", roomLabel))
                    socket.sendMessage(json)
                }

            }
        }
    }
}
