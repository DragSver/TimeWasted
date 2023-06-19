package com.example.timewaster.api

import com.example.timewaster.Activity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("activity")
    suspend fun getRandomActivity(): Response<Activity>

    @GET("activity?key=")
    suspend fun getByKey(
        @Query("key") key : String): Response<Activity>

    @GET("activity?type=")
    suspend fun getByType(
        @Query("type") type : String): Response<Activity>
}
