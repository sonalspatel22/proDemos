package com.example.prodemo.di.network.repository

import com.example.prodemo.di.network.request.PostRequest
import com.example.prodemo.di.network.utils.Resource
import com.example.prodemo.ui.model.BranchesResponse
import com.example.prodemo.ui.model.IssuesResponse
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.model.commiteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(private val postRequest: PostRequest) {

    fun getDirectory(ownerName: String, directoryName: String)= flow<Resource<DirectoryResponse>> {
        emit(Resource.loading())
        val posts = postRequest.getDirectory(ownerName, directoryName,)
        emit(Resource.success(posts))
    }.catch {
        emit(Resource.failed(it.message!!))
    }.flowOn(Dispatchers.IO)

    fun getBranches(ownerName: String, directoryName: String)= flow<Resource<BranchesResponse>> {
        emit(Resource.loading())
        val posts = postRequest.getBranches(ownerName, directoryName,)
        emit(Resource.success(posts))
    }.catch {
        emit(Resource.failed(it.message!!))
    }.flowOn(Dispatchers.IO)

    fun getIssues(ownerName: String, directoryName: String)= flow<Resource<IssuesResponse>> {
        emit(Resource.loading())
        val posts = postRequest.getIssues(ownerName, directoryName,)
        emit(Resource.success(posts))
    }.catch {
        emit(Resource.failed(it.message!!))
    }.flowOn(Dispatchers.IO)

    fun getCommites(ownerName: String, directoryName: String,branchName:String)= flow<Resource<commiteResponse>> {
        emit(Resource.loading())
        val posts = postRequest.getCommites(ownerName, directoryName,branchName)
        emit(Resource.success(posts))
    }.catch {
        emit(Resource.failed(it.message!!))
    }.flowOn(Dispatchers.IO)
}