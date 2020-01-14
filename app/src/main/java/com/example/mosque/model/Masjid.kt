package com.example.mosque.model

import com.google.gson.annotations.SerializedName


data class Masjid(
    @SerializedName("id") val id : Int?,
    @SerializedName("type") val type : Int?,
    @SerializedName("code") val code : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("identity") val identity : String?,
    @SerializedName("surface_area") val surface_area : String?,
    @SerializedName("building_area") val building_area : String?,
    @SerializedName("los") val los : String?,
    @SerializedName("since") val since : Int?,
    @SerializedName("bank_id") val bank_id : Int?,
    @SerializedName("rek") val rek : Int?,
    @SerializedName("address") val address : String?,
    @SerializedName("latitude") val latitude : String?,
    @SerializedName("longitude") val longitude : String?,
    @SerializedName("province_id") val province_id : Int?,
    @SerializedName("estimate") val estimate : Int?,
    @SerializedName("estimate_date") val estimate_date : String?,
    @SerializedName("city_id") val city_id : Int?,
    @SerializedName("kec_id") val kec_id : Int?,
    @SerializedName("kel_id") val kel_id : Int?,
    @SerializedName("pic") val pic : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("bank") val bank : Bank?,
    @SerializedName("province") val province : Province?,
    @SerializedName("regency") val regency : String?,
    @SerializedName("districts") val districts : String?,
    @SerializedName("village") val village : String?

)