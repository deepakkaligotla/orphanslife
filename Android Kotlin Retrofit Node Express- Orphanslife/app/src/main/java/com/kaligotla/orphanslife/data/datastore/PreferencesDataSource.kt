package com.kaligotla.orphanslife.data.datastore

import android.util.Log
import com.kaligotla.orphanslife.preferences.PreferenceStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PreferencesDataSource(private val preferenceStorage: PreferenceStorage) {
    fun savedKey(): Flow<Boolean> = preferenceStorage.savedKey()
    suspend fun setSavedKey(token: Boolean) = preferenceStorage.setSavedKey(token)

    fun API_Token(): Flow<String> = preferenceStorage.API_Token()
    suspend fun setAPI_Token(token: String) = preferenceStorage.setAPI_Token(token)

    fun LoggedInUser(): Flow<Int> = preferenceStorage.LoggedInUserID()
    suspend fun setLoggedInUser(token: Int) = preferenceStorage.setLoggedInUserID(token)

    fun Role(): Flow<String> = preferenceStorage.role()
    suspend fun setRole(token: String) = preferenceStorage.setRole(token)

    suspend fun clearAll() = preferenceStorage.clearAll()
}