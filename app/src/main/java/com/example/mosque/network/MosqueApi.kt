package com.example.mosque.network

import com.example.mosque.model.Fasilitas
import com.example.mosque.model.Jadwal
import com.example.mosque.model.LaporanModel
import com.example.mosque.model.Mosque
import com.example.mosque.model.respons.ApiRespons
import com.example.mosque.network.ApiEndPoint.Fasilitas
import com.example.mosque.network.ApiEndPoint.MOSQUE
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MosqueApi {

    @GET("{lokasi}/daily.json")
    fun getJadwalSholat(@Path ("lokasi") lokasi :String ,
                        @Query ("key") MuslimKey : String)  : Single<Jadwal>

    @GET("rest/public/mosques/{id}")
    fun getDetailMosque(@Path (value = "id") id: String): Single<ApiRespons.MosqueRespons>

    @GET (MOSQUE)
    fun getMosque(@Query("page") page: Int) : Observable<ApiRespons.MosquesRespons>

    @GET (MOSQUE)
    fun getMosqueMarker() : Observable<ApiRespons.MosquesRespons>

    @GET(Fasilitas)
    fun getFilteredMasjid() : Observable<List<Fasilitas>>

    @POST("rest/public/mosque_facilities")
    @FormUrlEncoded
    fun filterSubmit(@Field (value = "full_time") full_time: String,
                     @Field (value = "ac") ac: String,
                     @Field (value = "car_parking") car_parking: String,
                     @Field (value = "free_water") free_water: String,
                     @Field (value = "easy_access") easy_access: String) : Observable<ApiRespons.FilterRespons>

    @GET("rest/public/mosque_finance_details/neraca/{id}")
    fun getDetailLaporan(@Path (value = "id") id: String): Single<ApiRespons.LaporanRespons>


    @POST("rest/public/mosque_donations")
    @FormUrlEncoded
    fun donationSubmit(@Header("Authorization") token: String,
                       @Field (value = "mosque_identity") mosque_identity: Int,
                       @Field (value = "user_id") user_id: Int,
                       @Field (value = "bank") bank: String,
                       @Field (value = "date") date: String,
                       @Field (value = "sub_category_id") sub_category_id: Int,
                       @Field (value = "nominal") nominal: Int,
                       @Field (value = "status") status: Int): Observable<ApiRespons.DonationResponds>


    @POST("rest/public/register")
    @FormUrlEncoded
    fun register (@Field (value = "name") name: String,
                  @Field (value = "username") username: String,
                  @Field (value = "email") email: String,
                  @Field (value = "password") password: String): Single<ApiRespons.RegisterRespons>

    @POST("rest/public/login")
    @FormUrlEncoded
    fun login(@Field (value= "email") email: String,
              @Field (value= "password") password: String): Single<ApiRespons.LoginRespons>

}