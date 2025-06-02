package com.roque.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface ConnectionUtils {
    fun isNetworkAvailable(): Boolean

    class ConnectionUtilsImpl(
        private val applicationContext: Context
    ) : ConnectionUtils {

        override fun isNetworkAvailable(): Boolean {
            return try {
                requireNotNull(applicationContext) { "Application context cannot be null" }

                val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}