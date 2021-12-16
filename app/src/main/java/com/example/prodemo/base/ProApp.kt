package com.example.prodemo.base

import android.content.Context
import androidx.multidex.MultiDex
import com.example.prodemo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class ProApp : DaggerApplication() {

    companion object {
        fun getAppContext(): Context {
            return context
        }

        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Timber.plant(Timber.DebugTree())

    }

    override fun androidInjector(): AndroidInjector<Any?> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any?>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}