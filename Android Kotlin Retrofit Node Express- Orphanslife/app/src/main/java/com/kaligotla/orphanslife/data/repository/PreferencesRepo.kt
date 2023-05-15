package com.kaligotla.orphanslife.data.repository

import android.content.Context
import androidx.core.content.edit

class PreferencesRepo private constructor(context: Context) {

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences("store", Context.MODE_PRIVATE)

    //Saved Key
    val getSavedKey: Boolean get() =  sharedPreferences.getBoolean("SavedKey", false)
    fun setSavedKey(key: Boolean) {
        sharedPreferences.edit {
            putBoolean("SavedKey", key)
        }
    }

    //API_Token
    val getAPI_Token: String get() =  sharedPreferences.getString("API_Token", "").toString()
    fun setAPI_Token(key: String) {
        sharedPreferences.edit {
            putString("API_Token", key)
        }
    }

    //Logged In User ID
    val getLoggedInUserID: Int get() =  sharedPreferences.getInt("ID", 0)
    fun setLoggedInUserID(key: Int) {
        sharedPreferences.edit {
            putInt("ID", key)
        }
    }

    //Role
    val getRole: String get() = sharedPreferences.getString("Role", "").toString()
    fun setRole(key: String) {
        sharedPreferences.edit {
            putString("Role", key)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferencesRepo? = null

        fun getInstance(context: Context): PreferencesRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = PreferencesRepo(context)
                INSTANCE = instance
                instance
            }
        }
    }
}