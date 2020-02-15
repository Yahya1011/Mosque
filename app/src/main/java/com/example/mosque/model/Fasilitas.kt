package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Fasilitas(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String

)