package com.example.mosque.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val newReq = chain.request().newBuilder()
            .header("Accept", "application/json")
            .build()
        return chain.proceed(newReq)

    }

}