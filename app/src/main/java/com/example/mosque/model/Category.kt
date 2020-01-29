package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("nama")
    @Expose
    val nama: String
)