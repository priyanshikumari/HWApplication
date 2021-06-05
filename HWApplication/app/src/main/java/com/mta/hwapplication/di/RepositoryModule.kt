package com.assignment.weatherapp.di

import com.mta.hwapplication.repository.AuthRepository
import com.mta.hwapplication.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}