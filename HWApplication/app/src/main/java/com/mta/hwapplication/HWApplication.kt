package com.mta.hwapplication

import android.app.Application
import com.assignment.weatherapp.di.DaggerAppComponent
import com.mta.hwapplication.repository.AuthRepository
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class HWApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}