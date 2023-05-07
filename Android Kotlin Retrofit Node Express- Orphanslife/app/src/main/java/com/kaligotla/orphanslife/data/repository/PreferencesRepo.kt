package com.kaligotla.orphanslife.data.repository

import com.kaligotla.orphanslife.data.datastore.PreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepo @Inject constructor(private val preferencesDataSource: PreferencesDataSource) {

    val getSavedKey: Flow<Boolean> get() =  preferencesDataSource.savedKey()
    suspend fun setSavedKey(key: Boolean) {
        return preferencesDataSource.setSavedKey(key)
    }

    val getAPI_Token: Flow<String> get() = preferencesDataSource.API_Token()
    suspend fun setAPI_Token(key: String) {
        return preferencesDataSource.setAPI_Token(key)
    }

    val getLoggedInUserID: Flow<Int> get() =  preferencesDataSource.LoggedInUser()
    suspend fun setLoggedInUserID(id: Int) {
        return preferencesDataSource.setLoggedInUser(id)
    }

    val getRole: Flow<String> get() =  preferencesDataSource.Role()
    suspend fun setRole(key: String) {
        return preferencesDataSource.setRole(key)
    }

    suspend fun clearAll() = preferencesDataSource.clearAll()
}