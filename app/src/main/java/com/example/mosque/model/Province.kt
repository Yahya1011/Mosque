package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("name")
    @Expose
    val provinceName: String
)
        