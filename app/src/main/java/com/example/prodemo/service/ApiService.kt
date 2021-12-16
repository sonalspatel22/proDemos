package com.example.prodemo.service


import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("players-to-add")
    suspend fun getPlayers(
        @Field("user_id") userId: Int
    )

}