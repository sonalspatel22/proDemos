package com.example.prodemo.base

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class AppPrefrences @Inject constructor(mContext: Context) :PrefrenceModelClass {

    private val mPrefs: SharedPreferences = mContext.getSharedPreferences("UserPreferencesUser", Context.MODE_PRIVATE)

    override var Directorylist: String
        get() = mPrefs.getString(DIRECTORYLIST, "") ?: ""
        set(value) = mPrefs.edit().putString(DIRECTORYLIST, value).apply()

    fun clear() {
        mPrefs.edit().clear().apply()
    }
    companion object{
        private const val DIRECTORYLIST = "DIRECTORYLIST"
    }
}