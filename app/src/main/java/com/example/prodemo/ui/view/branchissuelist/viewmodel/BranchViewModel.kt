package com.example.prodemo.ui.view.branchissuelist.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.prodemo.base.AppPrefrences
import com.example.prodemo.base.BaseViewModel
import com.example.prodemo.di.network.repository.PostRepository
import com.example.prodemo.di.network.utils.Resource
import com.example.prodemo.ui.model.BranchesResponseItem
import com.example.prodemo.ui.model.CommiteRequestData
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.view.branchissuelist.BranchesFragment.Companion.RECENT_SEARCH
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BranchViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appPrefrance: AppPrefrences
) : BaseViewModel() {

    private val TAG = "BranchViewModel"
    private val _branchList by lazy { MutableLiveData<MutableList<BranchesResponseItem>>() }
    var branchList: LiveData<MutableList<BranchesResponseItem>> = _branchList

    private val _startCommiteSubject by lazy { MutableLiveData<CommiteRequestData>() }
    val startCommite: LiveData<CommiteRequestData> = _startCommiteSubject
    lateinit var selectedRepoData: DirectoryResponse
    fun getDirectoryBranches(selectedRepoData: DirectoryResponse) {
        selectedRepoData.let {
            it.let {
                viewModelScope.launch {
                    postRepository.getBranches(it.owner.login, it.name).collect {
                        when (it) {
                            is Resource.Failed -> Log.i(TAG, "Failed:${it.message}")
                            is Resource.Loading -> Log.i(TAG, "Loading")
                            is Resource.Success -> {
                                it.data.let {
                                    _branchList.postValue(it)
                                    Log.e("getDirectoryBranches", "" + it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun startCommitActivity(branchesResponseItem: BranchesResponseItem) {
        selectedRepoData.let {
            val request = CommiteRequestData(it.owner.login, it.name, branchesResponseItem.name)
            _startCommiteSubject.value = (request)
        }
    }

    fun getBranches(arguments: Bundle?) {
        selectedRepoData = arguments?.getSerializable(RECENT_SEARCH) as DirectoryResponse
        getDirectoryBranches(selectedRepoData)
    }

}