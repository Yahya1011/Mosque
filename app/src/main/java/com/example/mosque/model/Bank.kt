package com.example.mosque.model

import com.google.gson.annotations.SerializedName

data class Bank (
    @SerializedName("id") val id : Int,
    @SerializedName("code") val code : String,
    @SerializedName("name") val name : String
)