package com.mta.hwapplication.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.assignment.weatherapp.di.utils.ViewModelFactory
import com.mta.hwapplication.R
import com.mta.hwapplication.databinding.ActivityHomeBinding
import com.mta.hwapplication.databinding.FragmentProfileBinding
import com.mta.hwapplication.model.ProfileResponse
import com.mta.hwapplication.ui.LoginActivity
import com.mta.hwapplication.ui.viewmodel.ProfileViewModel
import com.mta.hwapplication.utils.DataManager
import com.mta.hwapplication.utils.DateUtil
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : DaggerFragment(R.layout.fragment_profile) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels{viewModelFactory}

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)

        profileViewModel.profileLiveData.observe(viewLifecycleOwner){
            handleProfileResult(it)
        }

        callProfileAPI()

        profileViewModel.isLoadingLiveData.observe(viewLifecycleOwner){
            if (it)progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
        }

        profileViewModel.errorMsgLiveData.observe(viewLifecycleOwner) {
            showErrorMessage(getString(it))
        }

        tvLogoutProfile.setOnClickListener {
            profileViewModel.logout()
        }

        profileViewModel.logoutLiveData.observe(viewLifecycleOwner) {
            logout()
        }
    }

    private fun logout() {
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    private fun handleProfileResult(it: ProfileResponse?) {

        it?.let {
            updateUI(it)
            DataManager.created_at = it.created_at
            DataManager.email = it.email
            DataManager.displayName = it.display_name
            DataManager.personKey = it.key
            DataManager.roleKey = it.role.key
            DataManager.rank = it.role.rank
        }
    }

    private fun updateUI(profileResponse: ProfileResponse) {
        tvName.text = profileResponse.display_name
        tvEmail.text = profileResponse.email
        tvProfileOlder.text =  "${DateUtil.getDateInDays(profileResponse.created_at)} Days old"
    }

    private fun callProfileAPI() {
        profileViewModel.getUserProfileApi()
    }

    private fun showErrorMessage(message: String?){
        if (message != null && message.trim().length > 0) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}