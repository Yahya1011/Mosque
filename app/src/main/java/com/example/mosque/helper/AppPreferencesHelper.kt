package com.example.mosque.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.mosque.common.Constans


class AppPreferencesHelper(context: Context) : PreferencesHelper {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(Constans.PREF_FILE_NAME,Context.MODE_PRIVATE)


    override fun getAccessToken(): String? {
       return mPrefs.getString(Constans.PREF_KEY_ACCESS_TOKEN,null)
    }

    override fun setAccessToken(accessToken: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_ACCESS_TOKEN, accessToken).apply()
    }

    override fun getCurrentUserEmail(): String? {
        return mPrefs.getString(Constans.PREF_KEY_CURRENT_USER_EMAIL,null)
    }

    override fun setCurrentUserEmail(email: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_CURRENT_USER_EMAIL, email).apply()
    }

    override fun getCurrentUserId(): Long? {
        val userId = mPrefs.getLong(Constans.PREF_KEY_CURRENT_USER_ID, Constans.NULL_INDEX)
        return if (userId == Constans.NULL_INDEX) null else userId
    }

    override fun setCurrentUserId(userId: Long?) {
        val id = userId ?: Constans.NULL_INDEX
        mPrefs.edit().putLong(Constans.PREF_KEY_CURRENT_USER_ID, id).apply()
    }

    override fun isLoginIn(): Boolean {
        return  mPrefs.getBoolean(Constans.PREF_KEY_ISLOGIN, false)
    }

    override fun setLoginIn(logIn: Boolean?) {
        logIn?.let {
            mPrefs.edit().putBoolean(Constans.PREF_KEY_ISLOGIN, it).apply()
        }
    }

    override fun clearToken() {
        mPrefs.edit().remove("PREF_KEY_ACCESS_TOKEN").apply()
    }

    override fun getRoleUser(): String? {
        return mPrefs.getString(Constans.PREF_KEY_USER_ROLE,null)
    }

    override fun setRoleUser(userRole: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_USER_ROLE,userRole).apply()
    }

    override fun clearRole() {
        mPrefs.edit().remove("PREF_KEY_USER_ROLE").apply()
    }

    override fun getUsername(): String? {
        return mPrefs.getString(Constans.PREF_KEY_CURRENT_USERNAME,null)
    }

    override fun setUsername(username: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_CURRENT_USERNAME,username).apply()
    }

    override fun getFullname(): String? {
        return mPrefs.getString(Constans.PREF_KEY_CURRENT_FULLNAME,null)
    }

    override fun setFullname(name: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_CURRENT_FULLNAME,name).apply()
    }


}