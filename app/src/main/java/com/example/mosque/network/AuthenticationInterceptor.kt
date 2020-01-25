package com.example.mosque.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    var token: String = ""


    fun Token(token: String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var newReq = chain.request()
        if (!token.isNullOrEmpty()) {
            val finalToken = "Bearer $token"
            newReq = newReq.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        }

        return chain.proceed(newReq)

    }

}