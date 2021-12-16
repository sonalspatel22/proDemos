package com.example.prodemo.di.network.request

import com.example.prodemo.ui.model.BranchesResponse
import com.example.prodemo.ui.model.IssuesResponse
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.model.commiteResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostRequest {

    @GET("{owner_name}/{directory_name}")
    suspend fun getDirectory(
        @Path("owner_name") ownerName: String,
        @Path("directory_name") directoryName: String,
    ): DirectoryResponse

    @GET("{owner_name}/{directory_name}/branches")
    suspend fun getBranches(
        @Path("owner_name") ownerName: String,
        @Path("directory_name") directoryName: String,
    ): BranchesResponse

    @GET("{owner_name}/{directory_name}/issues")
    suspend fun getIssues(
        @Path("owner_name") ownerName: String,
        @Path("directory_name") directoryName: String,
    ): IssuesResponse

    @GET("{owner_name}/{directory_name}/commits")
    suspend fun getCommites(
        @Path("owner_name") ownerName: String,
        @Path("directory_name") directoryName: String,
        @Query("sha") branchName: String
    ): commiteResponse
}