package com.kaligotla.orphanslife.data.repository

import com.kaligotla.orphanslife.data.datastore.RemoteDataSource
import com.kaligotla.orphanslife.model.entity.*
import com.kaligotla.orphanslife.model.response.LoginBody
import kotlinx.coroutines.flow.Flow

class MainRepository (private val remoteDataSource: RemoteDataSource){
    fun login(loginBody: LoginBody) = remoteDataSource.api(loginBody)
}