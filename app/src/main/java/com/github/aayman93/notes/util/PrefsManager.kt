package com.github.aayman93.notes.util

import android.content.Context
import androidx.core.content.edit

class PrefsManager(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(
        Constants.PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        sharedPrefs.edit {
            putInt(key, value)
        }
    }
}