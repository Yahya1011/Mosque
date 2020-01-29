package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FinanceModel (
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
    @SerializedName("mosque_finance")
    @Expose
    val mosqueFinance: List<MosqueFinance>,
    @SerializedName("category")
    @Expose
    val category: Category,
    @SerializedName("category_sub")
    @Expose
    val categorySub: CategorySub

)