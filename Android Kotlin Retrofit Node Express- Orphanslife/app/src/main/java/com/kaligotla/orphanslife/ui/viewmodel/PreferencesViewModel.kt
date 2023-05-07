package com.kaligotla.orphanslife.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import kotlinx.coroutines.launch

class PreferencesViewModel(private val preferencesRepo: PreferencesRepo): ViewModel() {

    val savedKey = preferencesRepo.getSavedKey.asLiveData()
    fun setSavedKey(key: Boolean) {
        viewModelScope.launch {
            preferencesRepo.setSavedKey(key)
        }
    }

    val API_Token = preferencesRepo.getAPI_Token.asLiveData()
    fun setAPI_Token(key: String) {
        viewModelScope.launch {
            preferencesRepo.setAPI_Token(key)
        }
    }

    val LoggedInUserID = preferencesRepo.getLoggedInUserID.asLiveData()
    fun setLoggedInUserID(key: Int) {
        viewModelScope.launch {
            preferencesRepo.setLoggedInUserID(key)
        }
    }

    val role = preferencesRepo.getRole.asLiveData()
    fun setRole(key: String) {
        viewModelScope.launch {
            preferencesRepo.setRole(key)
        }
    }

    suspend fun clearAll() = preferencesRepo.clearAll()
}