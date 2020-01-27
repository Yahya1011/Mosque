package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("username")
    @Expose
    val username : String,
    @SerializedName("email")
    @Expose
    val email : String
)
