package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MosqueModel (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("code")
    @Expose
    val code: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("identity")
    @Expose
    val identity: String,
    @SerializedName("surface_area")
    @Expose
    val surfaceArea: String,
    @SerializedName("building_area")
    @Expose
    val buildingArea: String,
    @SerializedName("los")
    @Expose
    val los: String,
    @SerializedName("since")
    @Expose
    val since: String,
    @SerializedName("rek")
    @Expose
    val rek: String,
    @SerializedName("address")
    @Expose
    val address: String,
    @SerializedName("latitude")
    @Expose
    val latitude: String,
    @SerializedName("longitude")
    @Expose
    val longitude: String,
    @SerializedName("estimate")
    @Expose
    val estimate: String,
    @SerializedName("estimate_date")
    @Expose
    val estimateDate: String,
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
    val regency: Regency,
    @SerializedName("district")
    @Expose
    val district: District,
    @SerializedName("village")
    @Expose
    val village: String
)