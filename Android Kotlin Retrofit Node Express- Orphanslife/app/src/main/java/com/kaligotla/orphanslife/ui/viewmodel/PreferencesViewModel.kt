package com.kaligotla.orphanslife.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kaligotla.orphanslife.preferences.PreferenceStorage
import kotlinx.coroutines.launch

class PreferencesViewModel (private val preferenceStorage: PreferenceStorage): ViewModel() {

    val savedKey = preferenceStorage.savedKey().asLiveData()
    fun setSavedKey(key: Boolean) {
        viewModelScope.launch {
            preferenceStorage.setSavedKey(key)
        }
    }

    val API_Token = preferenceStorage.API_Token().asLiveData()
    fun setAPI_Token(key: String) {
        viewModelScope.launch {
            preferenceStorage.setAPI_Token(key)
        }
    }

    val LoggedInUserID = preferenceStorage.LoggedInUserID().asLiveData()
    fun setLoggedInUserID(key: Int) {
        viewModelScope.launch {
            preferenceStorage.setLoggedInUserID(key)
        }
    }

    val role = preferenceStorage.role().asLiveData()
    fun setRole(key: String) {
        viewModelScope.launch {
            preferenceStorage.setRole(key)
        }
    }
}