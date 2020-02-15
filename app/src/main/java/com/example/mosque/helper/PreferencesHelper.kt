package com.example.mosque.helper

interface PreferencesHelper {

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

    fun getFullname(): String?

    fun setFullname(name: String?)

    fun getUsername(): String?

    fun setUsername(username: String?)

    fun getCurrentUserId(): Int

    fun setCurrentUserId(userId: Int)

    fun isLoginIn() : Boolean?

    fun setLoginIn(logIn : Boolean?)

    fun clearToken()

    fun getRoleUser(): String?

    fun setRoleUser(userRole : String?)

    fun clearRole()


    fun setLogout()
}