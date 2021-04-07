package com.example.cloudinteractivenevic.common

interface PreferencesHelper {

    companion object {
        const val PREFS_KEY_ACCESS_TOKEN = "PrefsKeyAccessToken"
        const val PREFS_KEY_AUTHORIZATIONS = "PrefsKeyAuthorizations"
    }
    var accessToken: String
    var authorizations: Map<String, List<String>>

    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getInt(key: String, defaultValue: Int): Int
    fun putInt(key: String, value: Int)
    fun getString(key: String, defaultValue: String): String
    fun putString(key: String, value: String)
    fun remove(key: String)

}