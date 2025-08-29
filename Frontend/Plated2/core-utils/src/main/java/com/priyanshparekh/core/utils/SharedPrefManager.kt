package com.priyanshparekh.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPrefManager {

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(Constants.SharedPrefs.USER_DETAILS_PREF, Context.MODE_PRIVATE)
    }

    fun clearPrefs() = prefs.edit { clear() }

    fun getToken() = prefs.getString(Constants.SharedPrefs.KEY_TOKEN, "")

    fun getUserId() = prefs.getLong(Constants.SharedPrefs.KEY_ID, -1L)

    fun getUsername() = prefs.getString(Constants.SharedPrefs.KEY_DISPLAY_NAME, "")

    fun getBio() = prefs.getString(Constants.SharedPrefs.KEY_BIO, "")

    fun getProfession() = prefs.getString(Constants.SharedPrefs.KEY_PROFESSION, "")

    fun getWebsite() = prefs.getString(Constants.SharedPrefs.KEY_WEBSITE, "")
}