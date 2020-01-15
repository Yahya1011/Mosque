package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JadwalSholat (
    @SerializedName("date_for")
    @Expose
    val tanggal : String,
    @SerializedName("fajr")
    @Expose
    val fajar :String,
    @SerializedName("shurooq")
    @Expose
    val subuh : String ,
    @SerializedName("dhuhr")
    @Expose
    val zuhur : String ,
    @SerializedName("asr")
    @Expose
    val ashar : String ,
    @SerializedName("maghrib")
    @Expose
    val maghrib : String ,
    @SerializedName("isha")
    @Expose
    val isya : String
)