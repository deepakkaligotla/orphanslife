package com.kaligotla.orphanslife.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store")

@Singleton
class PreferenceImpl @Inject constructor(@ApplicationContext context: Context) : PreferenceStorage {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val SAVED_KEY = booleanPreferencesKey("saved_key")
        val API_Token = stringPreferencesKey("API_Token")
        val loggedInUserID = intPreferencesKey("LoggedInUserID")
        val role = stringPreferencesKey("role")
    }

    override fun savedKey() = dataStore.data.catch { it ->
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.SAVED_KEY] ?: false
    }

    override suspend fun setSavedKey(order: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.SAVED_KEY] = order
        }
    }

   override fun API_Token() = dataStore.data.catch { it ->
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.API_Token] ?: ""
    }

    override suspend fun setAPI_Token(order: String) {
        dataStore.edit {
            it[PreferencesKeys.API_Token] = order
        }
    }

    override fun LoggedInUserID() = dataStore.data.catch { it ->
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.loggedInUserID] ?: 0
    }

    override suspend fun setLoggedInUserID(order: Int) {
        dataStore.edit {
            it[PreferencesKeys.loggedInUserID] = order
        }
    }

    override fun role() = dataStore.data.catch { it ->
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.role] ?: ""
    }

    override suspend fun setRole(order: String) {
        dataStore.edit {
            it[PreferencesKeys.role] = order
        }
    }
}