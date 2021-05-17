package com.betterfly.test.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class SharedPreference(ctx: Context) : SharedPreferences {

    companion object{
        internal var sharedPreference: SharedPreference? = null
        fun newInstance(context: Context): SharedPreference {
            if (sharedPreference == null) {

                sharedPreference = SharedPreference(context.applicationContext)

            }
            return sharedPreference!!
        }
    }

    val isUserLoggedIn = "isUserLoggedIn"
    val PREFS_NAME = "List_BetterFly"
    internal var SharedPreferences: SharedPreferences? = null
    private var gson: Gson? = null


    init {
        if (SharedPreferences == null) {

            //Log.d("NULO","SharedPreferences")
            SharedPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val gsonBuilder = GsonBuilder()
            gson = gsonBuilder.create()
        }
    }


    fun clear_all(){

        SharedPreferences!!.edit().clear().apply()


    }


    override fun getAll(): Map<String, *>? {
        return null
    }

    override fun getString(key: String, defValue: String?): String? {
        return null
    }

    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        return null
    }

    override fun getInt(key: String, defValue: Int): Int {
        return 0
    }

    override fun getLong(key: String, defValue: Long): Long {
        return 0
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return 0f
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return false
    }

    override fun contains(key: String): Boolean {
        return false
    }

    override fun edit(): SharedPreferences.Editor? {
        return null
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {}
    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {}

    fun get_token_web(KEY_TAG_CACHE: String): String {
        return if (SharedPreferences!!.contains(KEY_TAG_CACHE)) {
            SharedPreferences!!.getString(KEY_TAG_CACHE, "")!!
        } else {
            ""
        }
    }

    fun set_token_web(TAG_CHAT_CACHE: String, token: String) {
        val editor = SharedPreferences!!.edit()
        editor.putString(TAG_CHAT_CACHE, token)
        editor.commit()
    }


    fun isUserLoggedIn(): Boolean {
        return if (SharedPreferences!!.contains(isUserLoggedIn)) {
            SharedPreferences!!.getBoolean(isUserLoggedIn, false)
        } else {
            false

        }
    }

    fun set_isUserLoggedIn(isUserLoggedIn_: Boolean): Boolean {
        val editor = SharedPreferences!!.edit()
        editor.putBoolean(isUserLoggedIn, isUserLoggedIn_)
        editor.commit()
        return true
    }

}