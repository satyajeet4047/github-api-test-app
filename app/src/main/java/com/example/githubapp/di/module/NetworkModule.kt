package com.example.githubapp.di.module

import com.example.githubapp.data.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module(includes = [ViewModelFactoryModule::class])
class NetworkModule {

    private val BASE_URL = "https://api.github.com/"

    private var REQUEST_TIMEOUT : Long = 60

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = (original as Request).newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }
}