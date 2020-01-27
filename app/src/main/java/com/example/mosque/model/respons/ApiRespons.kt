package com.example.mosque.model.respons

import com.example.mosque.model.LoginModel
import com.example.mosque.model.Mosque
import com.example.mosque.model.RegisterModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiRespons {
    data class RegisterRespons(
        @SerializedName("data")
        @Expose
        val data: RegisterModel,
        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("status")
        @Expose
        val status: Boolean
    )

    data class LoginRespons(
        @SerializedName("data")
        @Expose
        val data: LoginModel,
        @SerializedName("0")
        @Expose
        val code: Int
    )

    data class MosqueRespons(
        @SerializedName("current_page")
        @Expose
        val currentPage: Int,
        @SerializedName("data")
        @Expose
        val data: List<Mosque>,
        @SerializedName("first_page_url")
        @Expose
        val firstPageUrl: String,
        @SerializedName("from")
        @Expose
        val from: Int,
        @SerializedName("last_page")
        @Expose
        val lastPage: Int,
        @SerializedName("last_page_url")
        @Expose
        val lastPageUrl: String,
        @SerializedName("next_page_url")
        @Expose
        val nextPageUrl: String,
        @SerializedName("path")
        @Expose
        val path: String,
        @SerializedName("per_page")
        @Expose
        val perPage: Int,
        @SerializedName("prev_page_url")
        @Expose
        val prevPageUrl: String,
        @SerializedName("to")
        @Expose
        val to: Int,
        @SerializedName("total")
        @Expose
        val total: Int
    )
}
