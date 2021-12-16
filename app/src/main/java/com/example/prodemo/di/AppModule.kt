package com.example.prodemo.di

import android.app.Application
import android.content.Context
import com.example.prodemo.base.AppPrefrences
import com.example.prodemo.base.PrefrenceModelClass
import com.example.prodemo.di.network.repository.PostRepository
import com.example.prodemo.di.network.request.PostRequest
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun providePreferenceModelClass(appPreferencesHelper: AppPrefrences): PrefrenceModelClass = appPreferencesHelper

    @Provides
    @Singleton
    fun provideServicesRepository(
        postRequest: PostRequest,
        userPreference: AppPrefrences
    ): PostRepository = PostRepository(postRequest)


    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}