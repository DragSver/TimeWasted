package com.example.timewaster.api

import com.example.timewaster.Activity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("activity/")
    suspend fun getRandomActivity(): Response<Activity>
}
