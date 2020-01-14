package com.example.mosque.network

import com.example.mosque.model.Masjid
import com.example.mosque.model.Mosque
import com.example.mosque.network.ApiEndPoint.MOSQUE
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface MosqueApi {
    @GET (MOSQUE)
    fun getMosque() : Observable<List<Masjid>>
}
