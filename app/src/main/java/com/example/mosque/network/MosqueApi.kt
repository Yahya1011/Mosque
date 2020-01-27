package com.example.mosque.network

import com.example.mosque.model.Jadwal
import com.example.mosque.model.LaporanModel
import com.example.mosque.model.Mosque
import com.example.mosque.model.respons.ApiRespons
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
    fun getMosque(@Query("page") page: Int) : Observable<ApiRespons.MosqueRespons>

    @GET("rest/public/mosque_finance_details/neraca/{id}")
    fun getDetailLaporan(@Path (value = "id") id: String): Single<List<LaporanModel>>


    @POST("rest/public/mosque_donations")
    @FormUrlEncoded
    fun donationSubmit(@Header("Authorization") token: String,
                       @Field (value = "mosque_identity") mosque_identity: String,
                       @Field (value = "user_id") user_id: String,
                       @Field (value = "bank") bank: String,
                       @Field (value = "date") date: String,
                       @Field (value = "sub_category_id") sub_category_id: String,
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