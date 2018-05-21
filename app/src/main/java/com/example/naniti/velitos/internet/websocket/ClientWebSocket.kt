package com.example.naniti.velitos.internet.websocket

import android.util.Log
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.WebSocketFrame
import java.io.IOException


import java.lang.Exception


import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException


class ClientWebSocket(private val listener: MessageListener, private val host: String) {
    var connection: WebSocket? = null
        private set


    fun connect() {
        if (connection != null) {
            reconnect()
        } else {
            try {
                Log.i("STATUDHOST",host)
                val factory = WebSocketFactory().setConnectionTimeout(5000)
                connection = factory.createSocket(  host)
                connection!!.addListener(SocketListener())
                connection!!.connect()
            } catch (e: WebSocketException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } //catch (e: NoSuchAlgorithmException) {
              //  e.printStackTrace()
            //}
        }
    }

    private fun reconnect() {
        try {
            connection = connection!!.recreate().connect()
        } catch (e: WebSocketException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun close() {
        connection!!.disconnect()
    }

    fun send(message: String?) {
        Log.d("socket",message)
        connection!!.sendText(message)
    }


    inner class SocketListener : WebSocketAdapter() {

        @Throws(Exception::class)
        override fun onConnected(websocket: WebSocket?, headers: Map<String, List<String>>?) {
            Log.d(TAG, "onConnected")
            super.onConnected(websocket, headers)
            Log.d(TAG, "onConnected")
        }

        override fun onTextMessage(websocket: WebSocket?, message: String?) {
            listener.onSocketMessage(message)
            Log.d(TAG, "Message --> " + message!!)
        }

        override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
            Log.d(TAG, "Error -->" + cause!!.message)

            //     reconnect()
        }

        override fun onDisconnected(websocket: WebSocket?,
                                    serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?,
                                    closedByServer: Boolean) {
            Log.d(TAG, "onDisconnected")
            if (closedByServer) {
                reconnect()
            }
        }

        override fun onUnexpectedError(websocket: WebSocket?, cause: WebSocketException?) {
            Log.d(TAG, "Error -->" + cause!!.message)
            reconnect()
        }

        @Throws(Exception::class)
        override fun onPongFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
            super.onPongFrame(websocket, frame)
            // websocket!!.sendPing("Are you there?")
        }
    }


    interface MessageListener {
        fun onSocketMessage(message: String?)
    }

    companion object {
        private val TAG = "Websocket"
    }
}