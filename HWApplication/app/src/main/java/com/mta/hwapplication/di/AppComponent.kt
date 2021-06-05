package com.assignment.weatherapp.di

import android.app.Application
import com.mta.hwapplication.HWApplication
import com.mta.hwapplication.di.ActivityModule
import com.mta.hwapplication.di.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, FragmentModule::class])
interface AppComponent : AndroidInjector<HWApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}