package com.innobles.appyhiremusharib.dataInjection

import com.innobles.appyhiremusharib.BuildConfig
import com.innobles.appyhiremusharib.networkcall.api.ApiHelper
import com.innobles.appyhiremusharib.networkcall.api.ApiHelperImpl
import com.innobles.appyhiremusharib.networkcall.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Musharib Ali on 31/10/20.
 * I.S.T Pvt. Ltd
 * musharib.ali@innobles.com
 */
@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL


    @Provides
    @Singleton
    fun provideOkHttp() = run {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(helper: ApiHelperImpl): ApiHelper = helper


}