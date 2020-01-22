package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LaporanModel(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("mosque_finance_id")
    @Expose
    val mosqueFinanceId: String,
    @SerializedName("category_id")
    @Expose
    val categoryId: String,
    @SerializedName("sub_category_id")
    @Expose
    val subCategoryId: String,
    @SerializedName("information")
    @Expose
    val information: String,
    @SerializedName("nominal")
    @Expose
    val nominal: String,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("user_id")
    @Expose
    val userId: String,
    @SerializedName("mosque_id")
    @Expose
    val mosqueId: String,
    @SerializedName("nama")
    @Expose
    val nama: String

)