package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Donation(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("mosque_identity")
    @Expose
    val mosqueIdentity: String,
    @SerializedName("user_id")
    @Expose
    val userId: String,
    @SerializedName("bank")
    @Expose
    val bank: String,
    @SerializedName("no_trans")
    @Expose
    val noTrans: String,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("contributor_name")
    @Expose
    val contributorName: String,
    @SerializedName("sub_category_id")
    @Expose
    val subCategoryId: String,
    @SerializedName("nominal")
    @Expose
    val nominal: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("pic")
    @Expose
    val pic: String,
    @SerializedName("mosque")
    @Expose
    val mosque: Mosque,
    @SerializedName("user")
    @Expose
    val donator: Donator,
    @SerializedName("category_sub")
    @Expose
    val categorySub: CategorySub
)