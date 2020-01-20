package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategorySub (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("category_id")
    @Expose
    val categoryId: String,
    @SerializedName("nama")
    @Expose
    val nama: String
)