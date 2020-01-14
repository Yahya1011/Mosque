package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("code")
    @Expose
    val bankCode: String,
    @SerializedName("name")
    @Expose
    val bankName: String
)