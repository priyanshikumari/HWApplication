package com.mta.hwapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.assignment.weatherapp.di.utils.ViewModelFactory
import com.mta.hwapplication.databinding.ActivityRegistrationBinding
import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.LoginRequest
import com.mta.hwapplication.model.RegistrationRequest
import com.mta.hwapplication.ui.viewmodel.AuthViewModel
import com.mta.hwapplication.utils.DataManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import javax.inject.Inject

class RegistrationActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val registrationViewModel: AuthViewModel by viewModels{viewModelFactory}
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registrationViewModel.authLiveData.observe(this){
            handleLoginResult(it)
        }

        btnSignupReg.setOnClickListener {
            signupButtonClick()
        }

        registrationViewModel.isLoadingLiveData.observe(this){
            if (it)binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
        }

        registrationViewModel.errorMsgLiveData.observe(this) {
            showErrorMessage(getString(it))
        }
    }

    private fun showErrorMessage(message: String?){
        if (message != null && message.trim().length > 0) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun signupButtonClick() {
        if (registrationViewModel.isEmailValid(etEmailReg.text.toString())
                && registrationViewModel.isFullNameValid(etNameReg.text.toString())
                && registrationViewModel.isPasswordValid(etPassReg.text.toString())
                && registrationViewModel.isPasswordValid(etConfirmPassReg.text.toString())
                && registrationViewModel.isPasswordSame(etPassReg.text.toString(), etConfirmPassReg.text.toString())){
            val registrationRequest = RegistrationRequest(etNameReg.text.toString(), etEmailReg.text.toString(), etPassReg.text.toString())
            registrationViewModel.callRegistrationApi(registrationRequest)
        }
    }

    private fun handleLoginResult(it: AuthResponse?) {
        it?.let {
            DataManager.authenticationToken = it.authentication_token
            DataManager.personKey = it.person.key
            DataManager.displayName = it.person.display_name
            DataManager.roleKey = it.person.role.key
            DataManager.rank = it.person.role.rank

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}