package com.example.mosque.network


import com.example.mosque.BuildConfig
import com.example.mosque.model.Masjid
import com.example.mosque.model.Mosque
import com.example.mosque.network.ApiEndPoint.BASE_URL
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Services {


    fun getHomeMosque() =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ApiWorker.gsonConverter)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.client)
        .build()
        .create(MosqueApi::class.java)





}