package com.example.prodemo.di.network

import android.app.Application
import android.content.Context
import com.example.prodemo.BuildConfig
import com.example.prodemo.R
import com.example.prodemo.base.AppPrefrences
import com.example.prodemo.di.network.request.PostRequest
import com.example.prodemo.di.network.utils.NetworkConstants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Hitesh on 24/12/17.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        appPreferencesHelper: AppPrefrences,
        networkObserver: NetworkObserver
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cacheDir = File(context.cacheDir, "HttpCache")
        val cache = Cache(cacheDir, cacheSize.toLong())
        val builder = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideNetworkObserver(application: Application): NetworkObserver {
        return NetworkObserver(application)
    }

    @Provides
    fun provideInsightService(retrofit: Retrofit): PostRequest {
        return retrofit.create(PostRequest::class.java)
    }



    @Provides
    @Singleton
    fun provideRetrofit(context: Context, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()) // Using create async means all api calls are automatically created asynchronously using OkHttp's thread pool
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().registerTypeAdapter(
                        Date::class.java,
                        DateDeserializer()
                    ).setLenient().create()
                )
            )
            .build()

    }

}
