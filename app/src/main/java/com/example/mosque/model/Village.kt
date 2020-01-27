package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Village(
    @SerializedName("name")
    @Expose
    val name: String
)