package com.example.naniti.velitos.baseclasses

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


class ConnectionReceiver : BroadcastReceiver() {


    interface ConnectionReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectionReceiverListener: ConnectionReceiverListener? = null
    }

    override fun onReceive(context: Context, p1: Intent) {
        if (connectionReceiverListener != null) {
            connectionReceiverListener!!.onNetworkConnectionChanged(
                    isConnectedOrConnecting(context)
            )
        }
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val conManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = conManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

}