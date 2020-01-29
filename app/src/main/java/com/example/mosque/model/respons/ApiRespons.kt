package com.example.mosque.model.respons

import com.example.mosque.model.FinanceModel
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
        @SerializedName("data")
        @Expose
        val data: Mosque,
        @SerializedName("0")
        @Expose
        val code: Int
    )
    
    data class DonationResponds(
        @SerializedName("success")
        @Expose
        val success: Boolean,
        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("code")
        @Expose
        val code: Int
    )

    data class FinanceRespons(
        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("data")
        @Expose
        val data: FinanceModel,
        @SerializedName("code")
        @Expose
        val code: Int
    )
}
