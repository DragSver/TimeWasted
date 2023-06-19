package com.example.timewaster.usecase

import com.example.timewaster.Activity
import retrofit2.Response

interface UseCase {
    suspend fun getRandomActivity(): Response<Activity>
    suspend fun getByKey(key: String): Response<Activity>
    suspend fun getByType(type : String): Response<Activity>
}