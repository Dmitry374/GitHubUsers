package com.example.githubusers.di

import com.example.githubusers.BuildConfig
import com.example.githubusers.common.Constants
import com.example.githubusers.network.ApiService
import com.example.githubusers.repository.GithubUserRepository
import com.example.githubusers.repository.GithubUsersPaginationDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // init logging interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubUsersPaginationDataSource(apiService: ApiService): GithubUsersPaginationDataSource {
        return GithubUsersPaginationDataSource(
            apiService
        )
    }

    @Singleton
    @Provides
    fun provideGithubUserRepository(githubUsersPaginationDataSource: GithubUsersPaginationDataSource): GithubUserRepository {
        return GithubUserRepository(githubUsersPaginationDataSource)
    }
}