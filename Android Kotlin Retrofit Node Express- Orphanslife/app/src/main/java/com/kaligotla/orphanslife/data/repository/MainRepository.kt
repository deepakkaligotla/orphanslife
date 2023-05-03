package com.kaligotla.orphanslife.data.repository

import com.kaligotla.orphanslife.data.datastore.RemoteDataSource
import com.kaligotla.orphanslife.model.LoginBody

class MainRepository (private val remoteDataSource: RemoteDataSource){
    fun login(loginBody: LoginBody) = remoteDataSource.api(loginBody   )
}