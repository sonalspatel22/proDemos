package com.example.prodemo.ui.view.branchissuelist.viewmodel

import android.content.Intent
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
import com.example.prodemo.ui.model.IssuesResponseItem
import com.example.prodemo.ui.view.branchissuelist.BranchIssueListActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BranchIssueViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appPrefrance: AppPrefrences
) : BaseViewModel() {

    private val TAG = "BranchViewModel"

    private val _openInBrowserSubject by lazy { MutableLiveData<String>() }
    val openInBrowser: LiveData<String> = _openInBrowserSubject

    private val _issueListSize by lazy { MutableLiveData<Int>() }
    var issueListSize: LiveData<Int> = _issueListSize

    private val _selectedRepoData by lazy { MutableLiveData<DirectoryResponse>() }
    var selectedRepoData: LiveData<DirectoryResponse> = _selectedRepoData

    private val _delete by lazy { MutableLiveData<DirectoryResponse>() }
    var deleteistSize: LiveData<DirectoryResponse> = _delete


    fun getData(intent: Intent?) {
        if (intent != null) {
            _selectedRepoData.value =
                intent.getSerializableExtra(BranchIssueListActivity.DirectoryResponse) as DirectoryResponse
        }
    }


    fun openInBrowser() {
        _selectedRepoData.value?.let {
            _openInBrowserSubject.value = (it.url)
        }
    }

    fun deleteSelectedRepo() {
        _delete.value = _selectedRepoData.value
    }

    fun setNull() {
        _delete.value=null
    }


}