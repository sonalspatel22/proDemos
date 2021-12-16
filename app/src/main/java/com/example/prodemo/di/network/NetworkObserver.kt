package com.example.prodemo.di.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkObserver(private val context: Context) {

    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

}