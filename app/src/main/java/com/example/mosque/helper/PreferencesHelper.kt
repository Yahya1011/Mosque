package com.example.mosque.helper

interface PreferencesHelper {

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

    fun getCurrentUserId(): Long?

    fun setCurrentUserId(userId: Long?)

    fun isLoginIn() : Boolean

    fun setLogin(logIn : Boolean)

    fun clearToken()

    fun getRoleUser(): String?

    fun setRoleUser(userRole : String?)
}