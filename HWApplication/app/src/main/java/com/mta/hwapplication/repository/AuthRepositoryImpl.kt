package com.mta.hwapplication.repository

import android.content.Context
import com.assignment.weatherapp.retrofit.ApiResponseWrapper
import com.assignment.weatherapp.retrofit.Resource
import com.assignment.weatherapp.retrofit.WebApiInterface
import com.mta.hwapplication.R
import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.LoginRequest
import com.mta.hwapplication.model.ProfileResponse
import com.mta.hwapplication.model.RegistrationRequest
import com.mta.hwapplication.network.InternetConnectionManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val webApiInterface: WebApiInterface,
    val internetConnectionManager: InternetConnectionManager): AuthRepository {

    override suspend fun callLoginApi(loginRequest: LoginRequest): Resource<AuthResponse> {
        return if (internetConnectionManager.isNetworkAvailable()) {
            try {
                val res = webApiInterface.login(loginRequest)
                ApiResponseWrapper.createLoginResponse(res);
            } catch (e: Throwable) {
                ApiResponseWrapper.createException(e);
            }
        }else{
            Resource.Error(R.string.internet_error)
        }
    }

    override suspend fun callSignupApi(registrationRequest: RegistrationRequest): Resource<AuthResponse> {
        return if (internetConnectionManager.isNetworkAvailable()) {
            try {
                val res = webApiInterface.signup(registrationRequest)
                ApiResponseWrapper.createSignupResponse(res);
            } catch (e: Throwable) {
                ApiResponseWrapper.createException(e);
            }
        }else{
            Resource.Error(R.string.internet_error)
        }
    }

    override suspend fun getUserProfile(): Resource<ProfileResponse> {
        return if (internetConnectionManager.isNetworkAvailable()) {
            try {
                val res = webApiInterface.myProfile()
                ApiResponseWrapper.createProfileResponse(res);
            } catch (e: Throwable) {
                ApiResponseWrapper.createException(e);
            }
        }else{
            Resource.Error(R.string.internet_error)
        }
    }
}