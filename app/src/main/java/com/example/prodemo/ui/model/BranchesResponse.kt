package com.example.prodemo.ui.model
import com.google.gson.annotations.SerializedName


class BranchesResponse : ArrayList<BranchesResponseItem>()

data class BranchesResponseItem(
    @SerializedName("commit")
    val commit: Commits,
    @SerializedName("name")
    val name: String,
    @SerializedName("protected")
    val `protected`: Boolean
)

data class Commits(
    @SerializedName("sha")
    val sha: String,
    @SerializedName("url")
    val url: String
)