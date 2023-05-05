package com.kaligotla.orphanslife.data.datastore

import android.util.Log
import com.kaligotla.orphanslife.data.api.ApiServices
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.response.LoginBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiServices: ApiServices) {
    fun api(loginBody: LoginBody) = flow {
        emit(apiServices.login(loginBody))
    }.catch {
        Log.e("login", "login: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)
}