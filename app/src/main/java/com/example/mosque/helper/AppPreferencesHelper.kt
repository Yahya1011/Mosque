package com.example.mosque.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.mosque.common.Constans
import com.example.mosque.common.Constans.PREF_KEY_CURRENT_USER_ID


class AppPreferencesHelper(context: Context) : PreferencesHelper {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(Constans.PREF_FILE_NAME,Context.MODE_PRIVATE)


    override fun getAccessToken(): String? {
       return mPrefs.getString(Constans.PREF_KEY_ACCESS_TOKEN,null)
    }

    override fun setAccessToken(accessToken: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_ACCESS_TOKEN,accessToken)
    }

    override fun getCurrentUserEmail(): String? {
        return mPrefs.getString(Constans.PREF_KEY_CURRENT_USER_EMAIL,null)
    }

    override fun setCurrentUserEmail(email: String?) {
        mPrefs.edit().putString(Constans.PREF_KEY_CURRENT_USER_EMAIL,email)
    }

    override fun getCurrentUserId(): Long? {
        val userId = mPrefs.getLong(Constans.PREF_KEY_CURRENT_USER_ID, Constans.NULL_INDEX)
        return if (userId == Constans.NULL_INDEX) null else userId
    }

    override fun setCurrentUserId(userId: Long?) {
        val id = userId ?: Constans.NULL_INDEX
        mPrefs.edit().putLong(PREF_KEY_CURRENT_USER_ID, id).apply()
    }

    override fun isLoginIn(): Boolean {
        return false
    }

    override fun setLogin(logIn: Boolean) {
        mPrefs.edit().putBoolean(Constans.PREF_KEY_ISLOGIN,true)
    }


}