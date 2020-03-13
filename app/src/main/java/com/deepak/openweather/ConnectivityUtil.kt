@file:Suppress("DEPRECATION")

package com.deepak.openweather

import android.content.Context
import android.net.ConnectivityManager


    val Context.isConnected: Boolean
        get() {
            return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }
