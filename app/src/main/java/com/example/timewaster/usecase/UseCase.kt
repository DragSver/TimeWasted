package com.example.timewaster.usecase

import com.example.timewaster.Activity
import retrofit2.Response

interface UseCase {
    suspend fun getRandomActivity(): Response<Activity>
}