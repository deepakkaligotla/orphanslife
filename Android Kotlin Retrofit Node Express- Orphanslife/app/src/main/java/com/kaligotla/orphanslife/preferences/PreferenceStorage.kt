package com.kaligotla.orphanslife.preferences

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {

    fun savedKey() : Flow<Boolean>
    suspend fun setSavedKey(order: Boolean)

    fun API_Token() : Flow<String>
    suspend fun setAPI_Token(order: String)

    fun LoggedInUserID() : Flow<Int>
    suspend fun setLoggedInUserID(order: Int)

    fun role() : Flow<String>
    suspend fun setRole(order: String)
}