package com.example.cloudinteractivenevic.common

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.cloudinteractivenevic.api.converter.defaultGson
import com.example.cloudinteractivenevic.common.PreferencesHelper.Companion.PREFS_KEY_ACCESS_TOKEN
import com.example.cloudinteractivenevic.common.PreferencesHelper.Companion.PREFS_KEY_AUTHORIZATIONS
import com.example.cloudinteractivenevic.extension.fromJson
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class DefaultPreferencesHelper(private val context: Context,
) : PreferencesHelper {

    init {

    }
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val gson: Gson by lazy {
        GsonBuilder().create()
    }

    override var accessToken: String
        get() = prefs.getString(PREFS_KEY_ACCESS_TOKEN, "") ?: ""
        set(value) {
            prefs.edit().putString(PREFS_KEY_ACCESS_TOKEN, value).apply()
        }

    override var authorizations: Map<String, List<String>>
        get() = defaultGson.fromJson(prefs.getString(PREFS_KEY_AUTHORIZATIONS, "")) ?: emptyMap()
        set(value) {
            prefs.edit()
                .putString(PREFS_KEY_AUTHORIZATIONS, defaultGson.toJson(value))
                .apply()
        }
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    override fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

}