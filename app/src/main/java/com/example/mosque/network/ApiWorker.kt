package com.example.mosque.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object ApiWorker {
    private var mClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null

    /**
     * Don't forget to remove Interceptors (or change Logging Level to NONE)
     * in production! Otherwise people will be able to see your request and response on Log Cat.
     */
    val client: OkHttpClient
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val httpBuilder = OkHttpClient.Builder()
                httpBuilder.connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(interceptor)
                    //.addInterceptor(AuthenticationInterceptor())/// show all JSON in logCat
                mClient = httpBuilder.build()
            }

            return mClient!!
        }

    val privateClient: OkHttpClient
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val httpBuilder = OkHttpClient.Builder()
                httpBuilder.connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(object : Interceptor {
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val newRequet : Request = chain.request().newBuilder()
                                .addHeader("authorization","bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiNDdlOTgyZmEwYTk3NDNlODgwNDYyYWZkZDdhOGM1NTU2ZDkxNzM3ZWIyZGM4NWNjMTg2MWE0MDg0M2VkZWUzNjhlNzRiOTYyNDk3MzMzYjYiLCJpYXQiOjE1NzkxNzEyNTgsIm5iZiI6MTU3OTE3MTI1OCwiZXhwIjoxNjEwNzkzNjU4LCJzdWIiOiI3OCIsInNjb3BlcyI6W119.e9hxgHXc784Pnkq5uPAPvZdHWL5rq2s-qLCi5bGk4HZt5PkrjzdoEYa21uQei3F5xaj0zgLO5qMTBJel46R_rcCAV-m1NvKgkT-02x3VxVUimbDGeko7Fc_vu_s1WWHt9qaCYsGnUNj0BuY0FO8FNLJgSIkQCM_c47hx0pyWEZ-aedavg0aJ6VrGc4X6-eMZ0pEc47cI82fq2mshiVdX28V2paVpegCsst1bVKRh17UvdHQCg9Xv0vcN1QDl4eBH517RPiJwWmVlVqZIJ3TDf_oSRf8m-oqz9D60PW_NAeXTT_SKRUppJtKF3HXf31f7t1NgAffGg07O754isW2DV6FbmgMgKctJ8mQL7q-fpmFCHSsFeCS858NVX0D3QA0oFMpTBmI8qXF_fm7AESQuk5H9ESPPwElqhWwRNATPRDyuPxFbB1JJRmTx4U9N_M1I5lBYla4LrHvwCUJZZApSEPyJq6F_Jikn_mstjhi9CRd-fP3mN2pkyyhrwELCtl6PwjLkWL5PX7WP_L4026wu4XZGZm79nDtf8b2W-jpGtCtvw38ZHztq6nu4KGJxgh_I3NM58MyTdjW_0GHYBsTH8fcZVt0BLwcc2Evp-l0lMlmSpptHoIeTzgJtsmUeFpHWXn8YIKUCotIIZQnmXbDvozoEubHig6Isw7nA6uWln1w")
                                .build()
                            return chain.proceed(newRequet)
                        }

                    })
                //.addInterceptor(AuthenticationInterceptor())/// show all JSON in logCat
                mClient = httpBuilder.build()
            }

            return mClient!!
        }


    val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverter!!
        }
}