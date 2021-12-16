package com.example.prodemo.di.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No network available, please check your WiFi or Data connection"
}