package com.kaligotla.orphanslife.data.api

import android.content.Context
import com.kaligotla.orphanslife.preferences.PreferenceImpl
import com.kaligotla.orphanslife.preferences.PreferenceStorage

class PreferencesConfig {
    companion object {
        fun getPrefService(context: Context): PreferenceStorage {
            return PreferenceImpl(context)
        }
    }
}