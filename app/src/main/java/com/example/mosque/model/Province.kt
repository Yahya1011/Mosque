package com.example.mosque.model

import com.google.gson.annotations.SerializedName

class Province (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)