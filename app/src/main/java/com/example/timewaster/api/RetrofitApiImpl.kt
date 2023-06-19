package com.example.timewaster.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class RetrofitApiImpl @Inject constructor () : RetrofitApi {
    val API_URL = "http://www.boredapi.com/api/"

    override fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create()
}