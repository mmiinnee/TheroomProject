package th.co.theroom.utils

import android.content.Context
import android.content.SharedPreferences
import th.co.theroom.base.AppConfig

class Preference {

    private object Holder {
        val INSTANCE = Preference()
    }

    companion object {
        val instance: Preference by lazy { Holder.INSTANCE }
    }

    private fun openEditor(context: Context): SharedPreferences.Editor {

        val sharedPref = context.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    fun putInt(context: Context, key: String, value: Int): Boolean {
        return openEditor(context)
                .putInt(key, value)
                .commit()
    }

    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        val sharedPref = context.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE)
        return sharedPref.getInt(key, defaultValue)
    }

    fun putString(context: Context, key: String, value: String?): Boolean {
        return openEditor(context)
                .putString(key, value)
                .commit()
    }

    fun getString(context: Context, key: String, defaultValue: String?): String? {
        val sharedPref = context.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE)
        return sharedPref.getString(key, defaultValue)
    }

    fun putBoolean(context: Context, key: String, value: Boolean): Boolean {
        return openEditor(context)
                .putBoolean(key, value)
                .commit()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        val sharedPref = context.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun putLong(context: Context, key: String, value: Long): Boolean {
        return openEditor(context)
                .putLong(key, value)
                .commit()
    }

    fun getLong(context: Context, key: String, defaultValue: Long): Long {
        val sharedPref = context.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE)
        return sharedPref.getLong(key, defaultValue)
    }

    fun clearPreference(context: Context, key: String): Boolean {
        return openEditor(context)
                .remove(key)
                .commit()
    }

    fun clearAllPreference(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        return editor.clear().commit()
    }
}

