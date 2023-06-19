package com.example.timewaster

import com.example.timewaster.api.RetrofitApi
import com.example.timewaster.api.RetrofitApiImpl
import com.example.timewaster.repository.Repository
import com.example.timewaster.repository.RepositoryImpl
import com.example.timewaster.usecase.UseCase
import com.example.timewaster.usecase.UseCaseImpl
import com.example.timewaster.viewmodel.ActivityViewModel
import dagger.Binds
import dagger.Component
import dagger.Module


@Component(modules = [
    NetworkModule ::class,
    UseCaseModule::class,
    RepositoryModule::class])
interface AppComponent {
    fun inject(repositoryImpl: RepositoryImpl)
    fun inject(activityViewModel: ActivityViewModel)
    fun getUseCase() : UseCaseImpl
}

@Module
abstract class NetworkModule  {
    @Binds
    abstract fun bindRetrofitApi(retrofitApiImpl: RetrofitApiImpl) : RetrofitApi
}

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindUseCase(useCaseImpl: UseCaseImpl): UseCase
}

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
