package com.elican.examplechatapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkCoordinator
constructor(
    private val baseUrl: String,
    private val allowLogging: Boolean
) {

    companion object {
        // region connection constants
        const val CONNECTION_TIMEOUT = 15L
        val CONNECTION_TIMEOUT_UNIT = TimeUnit.SECONDS
        // endregion
    }

    private val retrofit = buildRetrofit(getOkHttpClient())

    fun provideRetrofit() = retrofit

    private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit =
        with(Retrofit.Builder()) {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())

            build()
        }

    private fun getOkHttpClient(): OkHttpClient =
        with(OkHttpClient.Builder()) {
            connectTimeout(CONNECTION_TIMEOUT, CONNECTION_TIMEOUT_UNIT)
            if (allowLogging) {
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            }
            addInterceptor(RequestInterceptor())
            build()
        }
}