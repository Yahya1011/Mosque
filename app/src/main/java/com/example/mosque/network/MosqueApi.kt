package com.example.mosque.network

import com.example.mosque.model.Mosque
import com.example.mosque.network.ApiEndPoint.MOSQUE
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface MosqueApi {
    @GET (MOSQUE)
    fun getMosque() : Observable<List<Mosque>>
}
