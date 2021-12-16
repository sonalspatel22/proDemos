package com.example.prodemo.di

import android.app.Application
import com.example.prodemo.base.ProApp
import com.example.prodemo.di.network.NetworkModule
import com.sabrewinginfotech.reesguru.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuildersModule::class,
    AppModule::class,
    NetworkModule::class])

interface AppComponent : AndroidInjector<ProApp> {
    
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}