package com.kaligotla.orphanslife.data.datastore

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kaligotla.orphanslife.data.api.ApiServices
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.response.LoginBody
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModelFactory
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

    fun getAdminDetailsByID(apiToken: String, id: Int) = flow {
        emit(apiServices.getAdminDetailsByID(apiToken, id))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun successPaymentCount(apiToken: String) = flow {
        emit(apiServices.getSuccessPaymentCount(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun failedPaymentsCount(apiToken: String) = flow {
        emit(apiServices.getFailedPaymentsCount(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun newAdoptReqsCount(apiToken: String) = flow {
        emit(apiServices.getNewAdoptReqsCount(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun approvedAdoptReqsCount(apiToken: String) = flow {
        emit(apiServices.getApprovedAdoptReqsCount(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun rejectedAdoptReqsCount(apiToken: String) = flow {
        emit(apiServices.getRejectedAdoptReqsCount(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun monthwiseDonationsTotal(apiToken: String) = flow {
        emit(apiServices.getMonthwiseDonationsTotal(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun getLocations(apiToken: String) = flow {
        emit(apiServices.locations(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun getOrphanages(apiToken: String) = flow {
        emit(apiServices.orphanages(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun getAdmins(apiToken: String) = flow {
        emit(apiServices.admins(apiToken))
    }.catch {
        Log.e("Get Admin Details", "Get Admin Details: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)
}