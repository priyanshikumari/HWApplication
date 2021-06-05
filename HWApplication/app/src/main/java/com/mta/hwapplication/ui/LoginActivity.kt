package com.mta.hwapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.assignment.weatherapp.di.utils.ViewModelFactory
import com.mta.hwapplication.databinding.ActivityLoginBinding
import com.mta.hwapplication.model.AuthResponse
import com.mta.hwapplication.model.LoginRequest
import com.mta.hwapplication.ui.viewmodel.AuthViewModel
import com.mta.hwapplication.utils.DataManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val loginViewModel: AuthViewModel by viewModels{viewModelFactory}
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.authLiveData.observe(this){
            handleLoginResult(it)
        }

        btnLogin.setOnClickListener {
            loginButtonClick()
        }

        new_member.setOnClickListener {
            openSignupScreen()
        }

        loginViewModel.isLoadingLiveData.observe(this){
            if (it)binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
        }

        loginViewModel.errorMsgLiveData.observe(this) {
            showErrorMessage(getString(it))
        }
    }

    private fun openSignupScreen() {
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }

    private fun showErrorMessage(message: String?){
        if (message != null && message.trim().length > 0) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginButtonClick() {
        if (loginViewModel.isEmailValid(etEmail.text.toString()) && loginViewModel.isPasswordValid(etPass.text.toString())){
            val loginRequest = LoginRequest(etEmail.text.toString(), etPass.text.toString())
            loginViewModel.callLoginApi(loginRequest)
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