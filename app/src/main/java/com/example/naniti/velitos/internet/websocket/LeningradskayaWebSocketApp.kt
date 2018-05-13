package com.example.naniti.velitos.internet.websocket

import android.app.Application
import android.content.Context


import com.example.naniti.velitos.internet.websocket.LeningradskayaSocketConnection


class LeningradskayaWebSocketApp() : Application() {

    private lateinit var leningradskayaSocketConnection: LeningradskayaSocketConnection

   lateinit var  check:String
    override fun onCreate() {
        super.onCreate()
        check = "CHECKED"
        leningradskayaSocketConnection = LeningradskayaSocketConnection(this)
    }

    fun closeSocketConnection() {
        leningradskayaSocketConnection.closeConnection()
    }

    fun openSocketConnection() {
        leningradskayaSocketConnection.openConnection()
    }

    fun isSocketConnected(): Boolean {
        return leningradskayaSocketConnection.isConnected()
    }

    fun reconnect() {
        leningradskayaSocketConnection.openConnection()
    }

    fun sendMessage(message: String?) {
        leningradskayaSocketConnection.send(message)
    }


    init {
        instance = this
    }

    companion object {
        private var instance: LeningradskayaWebSocketApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}