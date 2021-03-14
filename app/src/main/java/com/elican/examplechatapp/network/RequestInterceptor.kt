package com.elican.examplechatapp.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Intercepts request and enriches with headers
 * and custom parameters
 */
class RequestInterceptor : Interceptor {
    companion object {
        // region request header constants
        const val CONTENT_TYPE_HEADER = "Content-Type"
        const val Accept_HEADER = "Accept"
        const val MEDIA_TYPE_VALUE = "application/json"
        // endregion

    }
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        with(chain.request().newBuilder()) {
            addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_VALUE)
            addHeader(Accept_HEADER, MEDIA_TYPE_VALUE)
            build()
        }
    )

}