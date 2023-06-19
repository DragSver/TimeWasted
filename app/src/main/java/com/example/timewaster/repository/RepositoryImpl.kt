package com.example.timewaster.repository

import com.example.timewaster.Activity
import com.example.timewaster.api.RetrofitApi
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor (var retrofitApi: RetrofitApi) : Repository {

    override suspend fun getRandomActivity(): Response<Activity> =
        retrofitApi.provideApiService().getRandomActivity()
    override suspend fun getByKey(key: String): Response<Activity> =
        retrofitApi.provideApiService().getByKey(key)

    override suspend fun getByType(type: String): Response<Activity> =
        retrofitApi.provideApiService().getByType(type)
}