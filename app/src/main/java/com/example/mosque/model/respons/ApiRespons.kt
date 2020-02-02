package com.example.mosque.model.respons


import com.example.mosque.model.LoginModel
import com.example.mosque.model.Mosque
import com.example.mosque.model.MosqueModel
import com.example.mosque.model.RegisterModel
import com.example.mosque.model.LaporanModel
import com.example.mosque.model.FinanceModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiRespons {
    data class RegisterRespons(
        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("data")
        @Expose
        val data: RegisterModel,
        @SerializedName("code")
        @Expose
        val code: Int
    )

    data class LoginRespons(
        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("data")
        @Expose
        val data: LoginModel,
        @SerializedName("code")
        @Expose
        val code: Int
    )

    data class MosqueRespons(

        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("data")
        @Expose
        val data: MosqueModel,
        @SerializedName("code")
        @Expose
        val code: Int
    )

    data class MosquesRespons(

        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("data")
        @Expose
        val data: Mosque,
        @SerializedName("code")
        @Expose
        val code: Int
    )

    data class LaporanRespons(
        @SerializedName("message")
        @Expose
        val message: String,
        @SerializedName("data")
        @Expose
        val data: List<LaporanModel>,
        @SerializedName("code")
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
