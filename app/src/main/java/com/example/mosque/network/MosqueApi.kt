package com.example.mosque.network

import com.example.mosque.R
import com.example.mosque.model.Jadwal
import com.example.mosque.model.Mosque
import com.example.mosque.network.ApiEndPoint.MOSQUE
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MosqueApi {
    @GET (MOSQUE)
    fun getMosque() : Observable<List<Mosque>>

    @GET("{lokasi}/daily.json")
    fun getJadwalSholat(@Path ("lokasi") lokasi :String ,
                        @Query ("key") MuslimKey : String)  : Single<Jadwal>



}
