package com.example.timewaster.repository

import com.example.timewaster.Activity
import com.example.timewaster.api.RetrofitApi
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor (var retrofitApi: RetrofitApi) : Repository {

    override suspend fun getRandomActivity(): Response<Activity> = retrofitApi.provideApiService().getRandomActivity()
}