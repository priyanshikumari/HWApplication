package com.assignment.weatherapp.retrofit

import com.mta.hwapplication.R
import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.ProfileResponse
import retrofit2.HttpException
import java.net.HttpURLConnection

class ApiResponseWrapper {

    companion object{
        fun createLoginResponse(res: AuthResponse): Resource<AuthResponse>{
            if (res!=null) {
                return Resource.Success(res)
            } else {
                return Resource.Error(R.string.server_error)
            }
        }

        fun createSignupResponse(res: AuthResponse): Resource<AuthResponse>{
            if (res!=null) {
                return Resource.Success(res)
            } else {
                return Resource.Error(R.string.server_error)
            }
        }

        fun createProfileResponse(res: ProfileResponse): Resource<ProfileResponse>{
            if (res!=null) {
                return Resource.Success(res)
            } else {
                return Resource.Error(R.string.server_error)
            }
        }

        fun <T>createException(e: Throwable): Resource<T>{
            if (e is HttpException) {
                if (e.code() == HttpURLConnection.HTTP_NOT_FOUND){
                    return Resource.Error(R.string.data_not_found)
                }
            }
            return Resource.Error(R.string.server_error)
        }
    }
}