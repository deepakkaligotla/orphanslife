package com.kaligotla.orphanslife.data.repository

import android.content.Context
import com.kaligotla.orphanslife.data.datastore.RemoteDataSource

class DBRepo (private val remoteDataSource: RemoteDataSource){

    fun getAdminDetailsByID(apiToken: String, id: Int) = remoteDataSource.getAdminDetailsByID(apiToken, id)
}