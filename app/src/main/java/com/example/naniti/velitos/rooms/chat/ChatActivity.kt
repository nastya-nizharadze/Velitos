package com.example.naniti.velitos.rooms.chat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.MessageSend
import com.example.naniti.velitos.internet.websocket.LeningradskayaWebSocketApp
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.coroutines.experimental.bg


import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.example.naniti.velitos.rooms.chat.Response.DetailMessage
import com.example.naniti.velitos.rooms.chat.Response.responseMessage
import com.example.naniti.velitos.rooms.chat.Response.responseMessageRefresh
import com.example.naniti.velitos.rooms.chat.Response.responseTrait
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    lateinit var username: String
    val gson: Gson = Gson()
    private lateinit var adapter: MessageAdapter
    lateinit var socket: LeningradskayaWebSocketApp
    lateinit var roomLabel: String


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
        Log.d("Start Register", "YEAP")
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        Log.d("Stop Register", "YEAP")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        socket = LeningradskayaWebSocketApp.applicationContext() as LeningradskayaWebSocketApp
        roomLabel = intent.getStringExtra("room_label")
        username = intent.getStringExtra("username")

        messageList.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this)
        messageList.adapter = adapter
        //DELEET AFTER MAKE
        launch(UI) {
            bg {
                socket.openSocketConnection()

            }.await()
            bg {
                val message = MessageSend(RECONNECT_CLIENT, "bla", roomLabel)
                val json = gson.toJson(message)
                socket.sendMessage(json)
            }.await()
            bg {
                loadMessages()
            }

        }

        btnSend.setOnClickListener {
            onMessageSend()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageReceived(json: String?) {
        val response = gson.fromJson(json, responseTrait::class.java)
        if ((response.action != null &&
                        response.action == MESSAGE &&
                        response.detail!!.label == roomLabel)) {
            val detail: DetailMessage = gson.fromJson(json, responseMessage::class.java).detail!!
            val message = Message(detail.username!!, detail.message!!, "time")
            adapter.addMessage(message)

        }
        if ((response.action != null &&
                        response.action == REFRESH_CHAT &&
                        response.detail!!.label == roomLabel)) {
            val conv = gson.fromJson(json, responseMessageRefresh::class.java)
            val messages = conv.detail!!.message!!.map { it -> Message(it!!.user!!, it.message!!, it.created.toString()) }
                    .toCollection(ArrayList()).reversed()

            adapter.messages.addAll(0, messages)
            adapter.notifyItemRangeInserted(0, messages.size)
            messageList.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun onMessageSend() {
        if (txtMessage.text.isNotEmpty()) {
            val userMessage = txtMessage.text.toString()
            val json = gson.toJson(MessageSend(SEND, userMessage, roomLabel))
            async(UI) {
                val message = Message(username, userMessage, "time")
                adapter.addMessage(message)
                messageList.scrollToPosition(adapter.itemCount - 1)
                socket.sendMessage(json)
            }
            resetInput()
        }
    }

    private fun resetInput() {
        // Clean text box
        txtMessage.text.clear()
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun loadMessages() {
        val json = gson.toJson(MessageSend(REFRESH_CHAT, "", roomLabel))
        socket.sendMessage(json)
    }

    companion object {
        val JOIN = "join"
        val LEAVE = "leave"
        val SEND = "send"
        val REFRESH_CHAT = "refresh_chat"
        val VIEW_TYPE_MY_MESSAGE = 1
        val VIEW_TYPE_OTHER_MESSAGE = 2
        val RECONNECT_CLIENT = "reconnect"
        val MESSAGE = "message"
    }

}
