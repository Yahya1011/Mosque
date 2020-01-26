package com.example.mosque.model.respons

import com.example.mosque.model.LoginModel
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
        val code : Int
    )
}
