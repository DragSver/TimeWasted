package com.example.timewaster.usecase

import com.example.timewaster.Activity
import com.example.timewaster.repository.Repository
import retrofit2.Response
import javax.inject.Inject


class UseCaseImpl @Inject constructor(private val repository: Repository) : UseCase {
    override suspend fun getRandomActivity(): Response<Activity> = repository.getRandomActivity()
    override suspend fun getByKey(key: String): Response<Activity> = repository.getByKey(key)
    override suspend fun getByType(type: String): Response<Activity> =
                repository.getByType(type)
}