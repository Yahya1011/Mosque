package com.example.mosque.network


import com.example.mosque.network.ApiEndPoint.BASE_URL
import com.example.mosque.network.ApiEndPoint.SHALAT_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Services {


    //get list masjid
    fun getHomeMosque() =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.client)
        .build()
        .create(MosqueApi::class.java)

    //get waktu sholat
    fun getLocation() =  Retrofit.Builder()
        .baseUrl(SHALAT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.client)
        .build()
        .create(MosqueApi::class.java)

    //get detail masjid
    fun getLaporanDetail() =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.client)
        .build()
        .create(MosqueApi::class.java)

    //get donation
    fun getPostDonation() =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.privateClient)
        .build()
        .create(MosqueApi::class.java)

    //get fasilitas list
    fun getFacilitiesList() =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.client)
        .build()
        .create(MosqueApi::class.java)

    //get post fasilitas
    fun getPostFilter() =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(ApiWorker.client)
        .build()
        .create(MosqueApi::class.java)
}