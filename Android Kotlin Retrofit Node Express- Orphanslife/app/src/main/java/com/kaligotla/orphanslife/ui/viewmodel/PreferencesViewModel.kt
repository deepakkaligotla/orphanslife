package com.kaligotla.orphanslife.ui.viewmodel

import androidx.lifecycle.*
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import kotlinx.coroutines.launch

class PreferencesViewModel (private val preferencesRepo: PreferencesRepo): ViewModel() {

    val savedKey = preferencesRepo.getSavedKey
    fun setSavedKey(key: Boolean) {
        viewModelScope.launch {
            preferencesRepo.setSavedKey(key)
        }
    }

    val API_Token = preferencesRepo.getAPI_Token
    fun setAPI_Token(key: String) {
        viewModelScope.launch {
            preferencesRepo.setAPI_Token(key)
        }
    }

    val LoggedInUserID = preferencesRepo.getLoggedInUserID
    fun setLoggedInUserID(key: Int) {
        viewModelScope.launch {
            preferencesRepo.setLoggedInUserID(key)
        }
    }

    val role = preferencesRepo.getRole
    fun setRole(key: String) {
        viewModelScope.launch {
            preferencesRepo.setRole(key)
        }
    }
}

class PreferencesViewModelFactory(
    private val preferencesRepo: PreferencesRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PreferencesViewModel(preferencesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}