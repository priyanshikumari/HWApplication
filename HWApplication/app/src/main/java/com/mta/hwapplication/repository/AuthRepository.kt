package com.mta.hwapplication.repository

import com.assignment.weatherapp.retrofit.Resource
import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.LoginRequest
import com.mta.hwapplication.model.ProfileResponse
import com.mta.hwapplication.model.RegistrationRequest

interface AuthRepository {

    suspend fun callLoginApi(loginRequest: LoginRequest): Resource<AuthResponse>
    suspend fun callSignupApi(registrationRequest: RegistrationRequest): Resource<AuthResponse>
    suspend fun getUserProfile(): Resource<ProfileResponse>
}