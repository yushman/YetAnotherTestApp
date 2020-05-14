package com.example.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteApi {

    private val API_BASE_URL = "https://kudago.com/"
    private val retrofit: Retrofit

    init {
        val gson = GsonBuilder().create()
        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }


    private fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(provideLogInterceptor())
        .build()

    private fun provideLogInterceptor() = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BASIC }

}