package com.mta.hwapplication.di

import com.mta.hwapplication.ui.HomeActivity
import com.mta.hwapplication.ui.LoginActivity
import com.mta.hwapplication.ui.RegistrationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract fun contributesLoginActivity(): LoginActivity

    @ContributesAndroidInjector()
    abstract fun contributesRegistrationActivity(): RegistrationActivity

    @ContributesAndroidInjector()
    abstract fun contributesHomeActivity(): HomeActivity
}