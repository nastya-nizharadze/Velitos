package com.example.naniti.velitos.internet.websocket

import android.content.Context
import com.google.gson.Gson
import android.os.Handler
import android.util.Log

import org.greenrobot.eventbus.EventBus

// ws://hserver.leningradskaya105.ru:6379/ws/chat/edf/?token=3df
// host ws://127.0.0.1:8000/ws/chat/edf?token=e3445
//host ws://echo.websocket.org
//host ws://hserver.leningradskaya105.ru:6379/ws/chat/
class LeningradskayaSocketConnection(private var context: Context) : ClientWebSocket.MessageListener {

    companion object {
        // val SERVER = "ws://echo.websocket.org"
        // val SERVER = "ws://hserver.leningradskaya105.ru:6379/ws/chat/edf/?token=3df"
        val SERVER = "ws://hserver.leningradskaya105.ru:6379/ws/chat/?token="
    }

    var clientWebSocket: ClientWebSocket? = null
    var gson = Gson()


    fun openConnection(token: String) {

        Log.i("onOPen", token)
        if (clientWebSocket != null) clientWebSocket!!.close()
        try {
            Log.i("onOPen", token)
            clientWebSocket = ClientWebSocket(this, "$SERVER$token")

            clientWebSocket!!.connect()
            Log.i("Websocket", "Socket connected by user ")
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun closeConnection() {
        if (clientWebSocket != null) {
            clientWebSocket!!.close()
            clientWebSocket = null
        }

    }

    fun isConnected(): Boolean {
        return clientWebSocket != null &&
                clientWebSocket!!.connection != null &&
                clientWebSocket!!.connection!!.isOpen
    }

    fun send(message: String?) {
        if (clientWebSocket != null)
            clientWebSocket!!.send(message)
    }

    override fun onSocketMessage(message: String?) {
        if (message != null) Log.i("onSocketMessage", message)
        else Log.i("onSockect", "Null")
        EventBus.getDefault().post(message)//TODO to classes
    }


}