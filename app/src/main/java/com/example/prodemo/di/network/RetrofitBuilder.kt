package com.example.coroutineexample.network

import com.example.prodemo.di.network.request.PostRequest
import com.example.prodemo.di.network.utils.NetworkConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder
{
    private fun getInstance():Retrofit{

//        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(BasicAuthInterceptor("44e361f0d31a0aac8aed21a76669a7ed","44e361f0d31a0aac8aed21a76669a7ed"))
//            .build()

        return Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val postRetrofit = getInstance().create(PostRequest::class.java)


}