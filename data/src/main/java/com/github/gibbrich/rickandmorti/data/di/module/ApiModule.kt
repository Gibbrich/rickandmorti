package com.github.gibbrich.rickandmorti.data.di.module

import android.content.ContentValues.TAG
import android.util.Log
import com.github.gibbrich.rickandmorti.data.api.Api
import com.github.gibbrich.rickandmorti.data.di.DataScope
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @DataScope
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Reusable
    @Provides
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Reusable
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message -> Log.d(TAG, message) }
        val loggingInterceptor = HttpLoggingInterceptor(logger)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Reusable
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}