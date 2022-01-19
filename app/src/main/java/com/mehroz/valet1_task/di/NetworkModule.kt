package com.mehroz.valet1_task.di

import co.infinum.retromock.Behavior
import co.infinum.retromock.Retromock
import com.mehroz.valet1_task.BuildConfig
import com.mehroz.valet1_task.data.remote.ApiService
import com.mehroz.valet1_task.utils.ResourceBodyFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

/*    @Singleton
    @Provides
    fun provideConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()*/

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            //.client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetroMock(retrofit: Retrofit): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit)
         //   .defaultBehavior { 0 }
        //    .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retroMock: Retromock): ApiService =
        retroMock.create(ApiService::class.java)
}