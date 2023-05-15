package com.kaligotla.orphanslife.data.repository

import android.content.Context
import com.kaligotla.orphanslife.data.datastore.RemoteDataSource
import com.kaligotla.orphanslife.model.entity.*
import com.kaligotla.orphanslife.model.response.LoginBody

class MainRepository (private val remoteDataSource: RemoteDataSource){
    fun login(loginBody: LoginBody) = remoteDataSource.api(loginBody)
}