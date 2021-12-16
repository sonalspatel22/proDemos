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
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.model.IssuesResponseItem
import com.example.prodemo.ui.view.branchissuelist.BranchesFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appPrefrance: AppPrefrences
) : BaseViewModel() {
    private val TAG = "IssueViewModel"
    private val _issueList by lazy { MutableLiveData<MutableList<IssuesResponseItem>>() }
    var issueList: LiveData<MutableList<IssuesResponseItem>> = _issueList

    private val _issueListSize by lazy { MutableLiveData<Int>() }
    var issueListSize: LiveData<Int> = _issueListSize

    lateinit var selectedRepoData: DirectoryResponse

    fun getDirectoryIssues(selectedRepoData: DirectoryResponse) {
       selectedRepoData.let {
            it.let {
                viewModelScope.launch {
                    postRepository.getIssues(it.owner.login, it.name).collect {
                        when (it) {
                            is Resource.Failed -> Log.i(TAG, "Failed:${it.message}")
                            is Resource.Loading -> Log.i(TAG, "Loading")
                            is Resource.Success -> {
                                it.data.let {
                                    _issueList.postValue(it)
                                    _issueListSize.postValue(it.size)
                                }
                                Log.i("getDirectory", "" + it.data)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getIssues(arguments: Bundle?) {
        selectedRepoData = arguments?.getSerializable(BranchesFragment.RECENT_SEARCH) as DirectoryResponse
        getDirectoryIssues(selectedRepoData)
    }
}