package com.example.prodemo.ui.view.commitelist

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.prodemo.base.AppPrefrences
import com.example.prodemo.base.BaseViewModel
import com.example.prodemo.di.network.repository.PostRepository
import com.example.prodemo.di.network.utils.Resource
import com.example.prodemo.ui.model.BranchesResponseItem
import com.example.prodemo.ui.model.CommiteRequestData
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.model.commiteResponseItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommiteViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appPrefrance: AppPrefrences
) : BaseViewModel() {

    val TAG = "PostActivity"

    var commiteList = MutableLiveData<MutableList<commiteResponseItem>>()
    var setTitle = MutableLiveData<String>()

    fun getDirectoryBranchesCommites(ownerName: String, directoryName: String, branchName: String) =
        viewModelScope.launch {
            postRepository.getCommites(ownerName, directoryName, branchName).collect {
                when (it) {
                    is Resource.Failed -> Log.i(TAG, "Failed:${it.message}")
                    is Resource.Loading -> Log.i(TAG, "Loading")
                    is Resource.Success -> {
                        it.data.let {
                            commiteList.postValue(it)
                        }
                        Log.e("DirectoryBranchCommit", "" + it.data)
                    }
                }
            }
        }

    fun getData(intent: Intent?) {

        val commitRequestData =
            intent?.getSerializableExtra(CommitesActivity.COMMITEREQ) as CommiteRequestData
        getDirectoryBranchesCommites(
            commitRequestData.ownerName,
            commitRequestData.repoName,
            commitRequestData.branchName
        )
        setTitle.value = commitRequestData.branchName

    }
}