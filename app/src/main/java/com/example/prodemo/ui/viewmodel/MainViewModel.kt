package com.example.prodemo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prodemo.base.AppPrefrences
import com.example.prodemo.base.BaseViewModel
import com.example.prodemo.di.network.repository.PostRepository
import com.example.prodemo.di.network.utils.Resource
import com.example.prodemo.ui.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.Serializable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appPrefrences: AppPrefrences
) : BaseViewModel() {

    lateinit var selectedBranch: BranchesResponseItem
    private val TAG = "MainViewModel"

    private val _directory by lazy { MutableLiveData<DirectoryResponse>() }
    val directory: LiveData<DirectoryResponse> = _directory


    private val _directoryList by lazy { MutableLiveData<MutableList<DirectoryResponse>>() }
    var directoryList: LiveData<MutableList<DirectoryResponse>> = _directoryList


    private val _commiteList by lazy { MutableLiveData<MutableList<commiteResponseItem>>() }
    var commiteList: LiveData<MutableList<commiteResponseItem>> = _commiteList


    init {
        Timber.tag("MAINVIEWMODEL").i(this.javaClass.name)
        _directoryList.value = getList() ?: kotlin.run { mutableListOf() }
    }

    fun setList(Directories: ArrayList<DirectoryResponse>) {
        val gson = Gson()
        val json = gson.toJson(Directories)//converting list to Json String
        appPrefrences.Directorylist = json
    }

    fun getList(): ArrayList<DirectoryResponse>? {
        val gson = Gson()
        val json = appPrefrences.Directorylist
        if (json.isNotEmpty()) {
            val type = object :
                TypeToken<ArrayList<DirectoryResponse>>() {}.type//converting the json to list
            return gson.fromJson(json, type)//returning the list
        }
        return null
    }

    @DelicateCoroutinesApi
    fun getDirectory(ownerName: String, directoryName: String) {
        GlobalScope.launch {
            postRepository.getDirectory(ownerName, directoryName).collect {
                when (it) {
                    is Resource.Failed -> {
                        errorSubject.onNext("Failed:${it.message}")
                    }
                    is Resource.Loading -> errorSubject.onNext("Loading")
                    is Resource.Success -> {
                        it.data.let {
                            _directory.postValue(it)
                            addDataInRepoList(it)
                        }
                        Log.i("getDirectory", "" + it.data)
                    }
                }
            }
        }
    }


    fun addDataInRepoList(directoryModel: Serializable?) {
        _directoryList.value?.let {
            if (!it.contains(directoryModel)!!) {
                it.add(directoryModel as DirectoryResponse)
                _directoryList.postValue(it)
                setList(it as ArrayList<DirectoryResponse>)
            }
        }
    }


    fun deleteSelectedRepo(dr: DirectoryResponse) {
        dr.let {
            _directoryList.value?.let { list ->
                list.remove(it)
                setList(list as ArrayList<DirectoryResponse>)
                _directoryList.postValue(list)
            }
        }
    }

    fun setNull() {
        _directory.value=null
    }

}




