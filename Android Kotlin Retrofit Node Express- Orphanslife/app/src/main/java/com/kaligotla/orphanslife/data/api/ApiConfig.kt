package com.kaligotla.orphanslife.data.api

import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        private val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        val gson = GsonBuilder()
            .setLenient()
            .create()
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.14:4000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        fun getApiService(): ApiServices = retrofit.create(ApiServices::class.java)
    }
}