package com.example.mosque.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Donator(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("role")
    @Expose
    val role: String,
    @SerializedName("remember_token")
    @Expose
    val rememberToken: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String
)