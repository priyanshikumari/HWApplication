package com.mta.hwapplication.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.weatherapp.retrofit.Resource
import com.mta.hwapplication.model.ProfileResponse
import com.mta.hwapplication.repository.AuthRepository
import com.mta.hwapplication.ui.LoginActivity
import com.mta.hwapplication.utils.DataManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        private val repository: AuthRepository
): ViewModel() {

    private var profileMutableLiveData = MutableLiveData<ProfileResponse>()
    var profileLiveData: LiveData<ProfileResponse> = profileMutableLiveData

    private val isLoading = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = isLoading

    private val errorMsg = MutableLiveData<Int>()
    val errorMsgLiveData: LiveData<Int> = errorMsg

    private val logout = MutableLiveData<Boolean>()
    val logoutLiveData: LiveData<Boolean> = logout

    fun getUserProfileApi(){
        viewModelScope.launch {
            isLoading.value = true
            val result =  repository.getUserProfile()
            isLoading.value = false
            when(result){
                is Resource.Success -> {
                    profileMutableLiveData.value = result.data!!
                }
                is Resource.Error -> {
                    errorMsg.value = result.resId
                }
            }
        }
    }
    fun logout() {
        isLoading.value = true
        DataManager.created_at = ""
        DataManager.email = ""
        DataManager.displayName = ""
        DataManager.personKey = ""
        DataManager.roleKey = ""
        DataManager.rank = 0
        DataManager.authenticationToken = ""
        DataManager.password = ""
        DataManager.updated_at = ""
        isLoading.value = false
        logout.value = true
    }
}