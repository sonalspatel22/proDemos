package com.example.prodemo.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected val isLoadingSubject = BehaviorSubject.create<Boolean>()
    val isLoading: Observable<Boolean> = isLoadingSubject.hide()

    protected val errorSubject = PublishSubject.create<String>()
    val error: Observable<String> = errorSubject.hide()

    protected val showMessageSubject = PublishSubject.create<Int>()
    val showMessage: Observable<Int> = showMessageSubject.hide()

    protected val tokenExpiredSubject = PublishSubject.create<Unit>()
    val tokenExpired: Observable<Unit> = tokenExpiredSubject.hide()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}