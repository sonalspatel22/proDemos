package com.example.prodemo.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DirectoryResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("owner")
    val owner: Owner
) : Serializable
//issue

data class Owner(
    @SerializedName("login")
    val login: String
) : Serializable

class IssuesResponse : ArrayList<IssuesResponseItem>()

data class IssuesResponseItem(
    @SerializedName("milestone")
    val milestone: Milestone,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: User
)

data class Milestone(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("description")
    val description: String,
)

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

data class Creator(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("login")
    val login: String,
)