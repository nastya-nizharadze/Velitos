package com.example.naniti.velitos.rooms.myRooms


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.naniti.velitos.MainActivity

import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.LeningradskayaClient
import com.example.naniti.velitos.rooms.chat.ChatActivity
import com.example.naniti.velitos.internet.RoomsInstance
import com.example.naniti.velitos.internet.websocket.LeningradskayaWebSocketApp
import com.example.naniti.velitos.rooms.chat.Response.responseMessage
import com.example.naniti.velitos.rooms.chat.Response.responseTrait
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_rooms_my_rooms.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.coroutines.experimental.bg


class RoomsMyRooms : Fragment()//, //SwipeRefreshLayout.OnRefreshListener {
{

    lateinit var httpClient: LeningradskayaClient
    private lateinit var adapter: MyRoomsAdapter
    private val gson = Gson()

    companion object {
        fun newInstance() = RoomsMyRooms()
    }

    override fun onStart() {
        super.onStart()
        async(UI) {
            EventBus.getDefault().register(this);
            Log.d("Start RegisterMYRooms", "YEAP")
        }

    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        Log.d("Stop RegisterMYRooms", "YEAP")
    }

    // сокеты всегда работают
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rooms_my_rooms, container, false)
        httpClient = (activity as MainActivity).httpClient
        val my_rooms = view.findViewById<RecyclerView>(R.id.my_rooms)
        my_rooms.layoutManager = LinearLayoutManager(activity)
        adapter = MyRoomsAdapter(activity, { room -> onRoomClicked(room) })
        my_rooms.adapter = adapter

        async(UI) {
            loadRooms()
        }
        return view
    }

    fun onRoomClicked(room: Room) {
        val chatActivity = Intent(activity, ChatActivity::class.java)
        chatActivity.putExtra("room_label", room.label)
        chatActivity.putExtra("username", (activity as MainActivity).username)
        startActivity(chatActivity)
    }

    private suspend fun loadRooms() {
        val rooms = httpClient.getUserRooms().await()!!.map {
            lateinit var msg: String
            if (it.messages!!.isEmpty()) msg = "No messages yet"
            else msg = it.messages.last()!!
            Room(it.name!!, msg, it.label!!)
        }.toCollection(ArrayList())
        adapter.rooms.addAll(0, rooms)
        adapter.notifyItemRangeInserted(0, rooms.size)
        my_rooms.scrollToPosition(adapter.itemCount - 1)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageRecieved(json: String?) {
        val response = gson.fromJson(json, responseTrait::class.java)
        if ((response.action != null && response.action == ChatActivity.MESSAGE)) {
            val resp = gson.fromJson(json, responseMessage::class.java)!!
            adapter.updateByLabel(resp.detail!!.label!!, resp.detail.message!!)//username also
        }
    }
}


// добавить челлендж на кнопку присоединится join//im nain start listen  socket
//listen on socket when