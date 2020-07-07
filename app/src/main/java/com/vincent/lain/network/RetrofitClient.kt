package com.vincent.lain.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val API_KEY = "e2cd9fec06864daf9d0f465121919fac"
    const val BASE_URL = "https://api.spoonacular.com"
    const val IMAGE_URL = "https://images.spoonacular.com/file/wximages/"

    val menusApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitInterface::class.java)
}