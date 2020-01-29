package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MosqueFinance (

    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("user_id")
    @Expose
    val userId: String,
    @SerializedName("mosque_id")
    @Expose
    val mosqueId: String
)