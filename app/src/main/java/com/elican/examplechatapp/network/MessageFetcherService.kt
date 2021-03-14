package com.elican.examplechatapp.network

import com.elican.examplechatapp.model.MessageResponse
import com.elican.examplechatapp.model.Response
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Call


interface MessageFetcherService {
    @GET
    fun getMessages(@Url url: String): Call<Response>
}