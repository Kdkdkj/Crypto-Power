package com.itbpower.crytocoins.Database

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

private const val PREFS_FILE_NAME = "coins"
private const val USER_CURRENCY = "currency"
private const val APP_VERSION_CODE = "APP_VERSION_CODE"


class UserPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PREFS_FILE_NAME,
            Context.MODE_PRIVATE
    )
    private val mEditor: SharedPreferences.Editor = sharedPreferences.edit()

    fun clearPrefs() {
        mEditor.apply {
            clear()
            commit()
        }
    }


    fun saveCurrency(currency: String?) {
        mEditor.putString(USER_CURRENCY, currency)
        mEditor.apply()
    }
    fun getUserCurrency(): String {
        return sharedPreferences.getString(USER_CURRENCY, "USD")!!
    }


    fun setRateUsCount(RateUsCount: Int, context: Context) {
        val pref = context.getSharedPreferences("SplashAppUpdate", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("RateUsCount", RateUsCount)
        editor.apply()
    }

    fun getRateUsCount(context: Context): Int {
        val pref = context.getSharedPreferences("SplashAppUpdate", Activity.MODE_PRIVATE)
        return pref.getInt("RateUsCount", 0)
    }

    //---------------->end Update app aler<------------------------------------
    //---------------->Rate US  alert <------------------------------------
    fun setRateUsDontaskagain(isupdate: Boolean, context: Context) {
        val pref = context.getSharedPreferences("SplashAppUpdate", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("RateUsDontask", isupdate)
        editor.apply()
    }

    fun getRateUsDontaskagain(context: Context): Boolean {
        val pref = context.getSharedPreferences("SplashAppUpdate", Activity.MODE_PRIVATE)
        return pref.getBoolean("RateUsDontask", false)
    }

    fun createAppVersionCode(versionCode: Int) {
        mEditor.putInt(APP_VERSION_CODE, versionCode)
        mEditor.apply()
    }

    fun getAppVersionCode(): Int {
        return sharedPreferences.getInt(APP_VERSION_CODE, 0) // as default version code is 0
    }

}