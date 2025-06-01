package com.roque.data.network

import com.roque.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val MAX_TIMEOUT = 20

    fun create(url:String, headerInterceptor: HeaderInterceptor): Retrofit {
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(MAX_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(MAX_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(MAX_TIMEOUT.toLong(), TimeUnit.SECONDS)

        builder.addInterceptor(interceptor()).addInterceptor(headerInterceptor)

        return Retrofit.Builder()
            .baseUrl(url)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor   = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

}