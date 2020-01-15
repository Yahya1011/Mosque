package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Jadwal (
    @SerializedName("items")
    @Expose
    val items : List<JadwalSholat>,
    val status_valid: String
)