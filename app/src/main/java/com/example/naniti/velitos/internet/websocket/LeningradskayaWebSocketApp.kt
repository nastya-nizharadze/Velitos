package com.example.naniti.velitos.internet.websocket

import android.app.Application
import android.content.Context
import android.util.Log


import com.example.naniti.velitos.internet.websocket.LeningradskayaSocketConnection


class LeningradskayaWebSocketApp() : Application() {

    private lateinit var leningradskayaSocketConnection: LeningradskayaSocketConnection

    lateinit var check: String
    override fun onCreate() {
        super.onCreate()
        check = "CHECKED"
    }

    fun closeSocketConnection() {
        leningradskayaSocketConnection.closeConnection()
    }

    fun openSocketConnection() {
        Log.i("OPENSOCKET","open")
        val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        leningradskayaSocketConnection = LeningradskayaSocketConnection(this)
        leningradskayaSocketConnection.openConnection(pref.getString("JWTTOKEN", ""))
    }

    fun isSocketConnected(): Boolean {
        return leningradskayaSocketConnection.isConnected()
    }

    fun reconnect() {
        val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        leningradskayaSocketConnection.openConnection(pref.getString("JWTTOKEN", ""))

    }

    fun sendMessage(message: String?) {
        leningradskayaSocketConnection.send(message)
    }


    init {
        instance = this
    }

    companion object {
        private var instance: LeningradskayaWebSocketApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}