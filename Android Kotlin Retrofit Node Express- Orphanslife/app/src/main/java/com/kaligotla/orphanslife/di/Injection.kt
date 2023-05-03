package com.kaligotla.orphanslife.di

import android.content.Context
import com.kaligotla.orphanslife.data.api.ApiConfig
import com.kaligotla.orphanslife.data.datastore.RemoteDataSource
import com.kaligotla.orphanslife.data.repository.MainRepository

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val apiServices = ApiConfig.getApiService()
        val remoteDataSource = RemoteDataSource(apiServices)
        return MainRepository(remoteDataSource)
    }
}