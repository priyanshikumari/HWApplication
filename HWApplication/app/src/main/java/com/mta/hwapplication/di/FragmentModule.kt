package com.mta.hwapplication.di

import com.mta.hwapplication.ui.LoginActivity
import com.mta.hwapplication.ui.RegistrationActivity
import com.mta.hwapplication.ui.ui.dashboard.MapFragment
import com.mta.hwapplication.ui.ui.home.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector()
    abstract fun contributesMapFragment(): MapFragment

    @ContributesAndroidInjector()
    abstract fun contributesProfileFragment(): ProfileFragment
}