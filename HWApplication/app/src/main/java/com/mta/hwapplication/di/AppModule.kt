package com.assignment.weatherapp.di

import android.app.Application
import android.content.Context
import com.assignment.weatherapp.retrofit.WebApiInterface
import com.mta.hwapplication.di.utils.ViewModelModule
import com.mta.hwapplication.network.InternetConnectionManager
import com.mta.hwapplication.utils.AppConstants
import com.mta.hwapplication.utils.DataManager
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, RepositoryModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): Converter.Factory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("BaseURL")
    fun provideBaseURL() = AppConstants.WEATHER_API_BASE_URL

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

/*    @Singleton
    @Provides
    fun provideAppInterceptor(): Interceptor {
        return HeaderInterceptor()
    }*/

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                    request()
                            .newBuilder()
                            .addHeader("Authorization", DataManager.authenticationToken)
                            .addHeader("Content-Type", "application/json")
                            .build()
            )
        }
    }

    @Singleton
    @Provides
    fun provideOKHTTPClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(AppConstants.HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.HTTP_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.HTTP_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor {
                    val newRequest: Request = it.request().newBuilder()
                            .addHeader("Authorization", DataManager.authenticationToken)
                            .addHeader("Content-Type", "application/json ")
                            .build()
                    it.proceed(newRequest) }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseURL") baseURL: String, gsonConverter: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(gsonConverter).client(client).build()
    }

    @Singleton
    @Provides
    internal fun provideWebApiInterface(retrofit: Retrofit): WebApiInterface {
        return retrofit.create(WebApiInterface::class.java)
    }


    @Provides
    @Singleton
    fun provideInternetConnectionManager(context: Context): InternetConnectionManager {
        return InternetConnectionManager(context)
    }
}