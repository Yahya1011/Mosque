package com.example.mosque.network

import com.example.mosque.model.Jadwal
import com.example.mosque.model.LaporanModel
import com.example.mosque.model.Mosque
import com.example.mosque.network.ApiEndPoint.MOSQUE
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MosqueApi {

    @GET("{lokasi}/daily.json")
    fun getJadwalSholat(@Path ("lokasi") lokasi :String ,
                        @Query ("key") MuslimKey : String)  : Single<Jadwal>

    @GET("rest/public/mosques/{id}")
    fun getDetailMosque(@Path (value = "id") id: String): Single<Mosque>

    @GET (MOSQUE)
    fun getMosque() : Observable<List<Mosque>>

    @GET("rest/public/mosque_finance_details/{id}")
    fun getDetailLaporan(@Path (value = "id") id: String): Single<List<LaporanModel>>


    @POST("rest/public/mosque_donations")
    @FormUrlEncoded
    fun donationSubmit(@Query (value = "mosque_identity") mosque_identity: String,
                       @Query (value = "user_id") user_id: String,
                       @Query (value = "bank") bank: String,
                       @Query (value = "date") date: String,
                       @Query (value = "sub_category_id") sub_category_id: String,
                       @Query (value = "nominal") nominal: Int,
                       @Query (value = "status") status: Int): Observable<Mosque>
}