package com.example.timewaster.repository

import com.example.timewaster.Activity
import retrofit2.Response

interface Repository {
    suspend fun getRandomActivity(): Response<Activity>
}