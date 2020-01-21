package com.example.mosque.model


import com.google.gson.annotations.SerializedName

data class LaporanModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("mosque_finance_id")
    val mosque_finance_id : Int,
    @SerializedName("category_id")
    val category_id : Int,
    @SerializedName("sub_category_id")
    val sub_category_id : Int,
    @SerializedName("information")
    val information : String,
    @SerializedName("nominal")
    val nominal : Double,
    @SerializedName("mosque_finance")
    val mosque_finance : String,
    @SerializedName("category")
    val category : String,
    @SerializedName("category_sub")
    val category_sub : String
)