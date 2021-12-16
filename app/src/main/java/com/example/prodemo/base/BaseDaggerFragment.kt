package com.example.prodemo.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseDaggerFragment : DaggerFragment() {

    lateinit var baseActivity: AppCompatActivity
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            baseActivity = context
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun showToast(message: String) {
        view?.makeSnack(message)
    }
}
