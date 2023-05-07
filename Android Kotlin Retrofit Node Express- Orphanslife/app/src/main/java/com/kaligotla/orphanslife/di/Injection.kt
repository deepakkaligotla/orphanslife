package com.kaligotla.orphanslife.di

import android.content.Context
import com.kaligotla.orphanslife.data.api.ApiConfig
import com.kaligotla.orphanslife.data.api.PreferencesConfig
import com.kaligotla.orphanslife.data.datastore.PreferencesDataSource
import com.kaligotla.orphanslife.data.datastore.RemoteDataSource
import com.kaligotla.orphanslife.data.repository.MainRepository
import com.kaligotla.orphanslife.data.repository.PreferencesRepo

object Injection {
    fun mainRepository(context: Context): MainRepository {
        val apiServices = ApiConfig.getApiService()
        val remoteDataSource = RemoteDataSource(apiServices)
        return MainRepository(remoteDataSource)
    }
    fun prefRepository(context: Context): PreferencesRepo {
        val preferenceStorage = PreferencesConfig.getPrefService(context)
        val preferencesDataSource = PreferencesDataSource(preferenceStorage)
        return PreferencesRepo(preferencesDataSource)
    }
}