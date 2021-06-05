package com.assignment.weatherapp.retrofit

import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.LoginRequest
import com.mta.hwapplication.model.ProfileResponse
import com.mta.hwapplication.model.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebApiInterface {
    @POST("/api/v2/people/authenticate")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResponse

    @POST("/api/v2/people/create")
    suspend fun signup(@Body signupRequest: RegistrationRequest): AuthResponse

    @POST("/api/v2/people/reset_password")
    suspend fun resetPassword(@Body email: String): String

    @GET("/api/v2/people/me")
    suspend fun myProfile(): ProfileResponse

    @GET("/api/v2/people/")
    suspend fun profileByKey(@Body signupRequest: RegistrationRequest): AuthResponse
}