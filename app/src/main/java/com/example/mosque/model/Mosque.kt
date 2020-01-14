package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mosque(
    @SerializedName("id")
    @Expose
    val mosqueId: Int,
    @SerializedName("type")
    @Expose
    val mosqueType: String,
    @SerializedName("code")
    @Expose
    val mosqueCode: String,
    @SerializedName("name")
    @Expose
    val mosqueName: String,
    @SerializedName("identity")
    @Expose
    val mosqueIdentity: String,
    @SerializedName("surface_area")
    @Expose
    val surfaceArea: String,
    @SerializedName("building_area")
    @Expose
    val buildingArea: String,
    @SerializedName("los")
    @Expose
    val mosqueLos: String,
    @SerializedName("since")
    @Expose
    val mosqueSince: String,
    @SerializedName("bank_id")
    @Expose
    val bankId: String,
    @SerializedName("rek")
    @Expose
    val mosqueRek: String,
    @SerializedName("address")
    @Expose
    val mosqueAddress: String,
    @SerializedName("latitude")
    @Expose
    val latitude: String,
    @SerializedName("longitude")
    @Expose
    val longitude: String,
    @SerializedName("province_id")
    @Expose
    val provinceId: String,
    @SerializedName("estimate")
    @Expose
    val estimate: String,
    @SerializedName("estimate_date")
    @Expose
    val estimateDate: String,
    @SerializedName("city_id")
    @Expose
    val cityId: String,
    @SerializedName("kec_id")
    @Expose
    val kecId: String,
    @SerializedName("kel_id")
    @Expose
    val kelId: String,
    @SerializedName("pic")
    @Expose
    val pic: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("bank")
    @Expose
    val bank: Bank,
    @SerializedName("province")
    @Expose
    val province: Province,
    @SerializedName("regency")
    @Expose
    val regency: String,
    @SerializedName("districts")
    @Expose
    val districts: String,
    @SerializedName("village")
    @Expose
    val village: String
)
   