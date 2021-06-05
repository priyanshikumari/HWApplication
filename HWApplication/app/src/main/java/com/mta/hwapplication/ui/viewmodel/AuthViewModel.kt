package com.mta.hwapplication.ui.viewmodel

import android.R.attr.password
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.weatherapp.retrofit.Resource
import com.mta.hwapplication.R
import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.LoginRequest
import com.mta.hwapplication.model.RegistrationRequest
import com.mta.hwapplication.repository.AuthRepository
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class AuthViewModel @Inject constructor(
        private val repository: AuthRepository
): ViewModel() {

    private var authMutableLiveData = MutableLiveData<AuthResponse>()
    var authLiveData: LiveData<AuthResponse> = authMutableLiveData

    private val isLoading = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = isLoading

    private val errorMsg = MutableLiveData<Int>()
    val errorMsgLiveData: LiveData<Int> = errorMsg

    fun isEmailValid(email: String): Boolean{
        if (TextUtils.isEmpty(email)) {
            errorMsg.value = R.string.please_enter_email
            return false;
        } else {
            var isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (isValid){
               return true
            }else{
                errorMsg.value = R.string.invalid_email
                return false
            }
        }
    }

    fun isFullNameValid(name: String): Boolean{
        return if (name.isNotEmpty()){
            true
        }else{
            errorMsg.value = R.string.please_enter_full_name
            false
        }
    }

    fun isPasswordSame(firstPass: String, secondPass: String): Boolean{
        return if (firstPass == secondPass){
            true
        }else{
            errorMsg.value = R.string.pass_and_conf_pass_same
            false
        }
    }

    fun isPasswordValid(pass: String): Boolean{

        val pattern: Pattern

        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(pass)

        if (matcher.matches()){
            return true
        }else{
            errorMsg.value = R.string.invalid_password
            return false
        }
//        return pass.isNotEmpty()
    }

    fun callLoginApi(loginRequest: LoginRequest){
            viewModelScope.launch {
                isLoading.value = true
                val result =  repository.callLoginApi(loginRequest)
                isLoading.value = false
                when(result){
                    is Resource.Success -> {
                        authMutableLiveData.value = result.data!!
                    }
                    is Resource.Error -> {
                        errorMsg.value = result.resId
                    }
                }
            }
    }

    fun callRegistrationApi(registrationRequest: RegistrationRequest){
        viewModelScope.launch {
            isLoading.value = true
            val result =  repository.callSignupApi(registrationRequest)
            isLoading.value = false
            when(result){
                is Resource.Success -> {
                    authMutableLiveData.value = result.data!!
                }
                is Resource.Error -> {
                    errorMsg.value = result.resId
                }
            }
        }
    }
}