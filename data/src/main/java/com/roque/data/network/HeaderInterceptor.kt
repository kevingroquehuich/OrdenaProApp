package com.roque.data.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(/*private val preferences: PreferenceManager*/) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            //.addHeader("Authorization", preferences.getAuthToken().toString())
            .build()
        return chain.proceed(request)
    }
}