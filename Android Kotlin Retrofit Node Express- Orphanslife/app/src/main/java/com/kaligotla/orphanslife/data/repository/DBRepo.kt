package com.kaligotla.orphanslife.data.repository

import com.kaligotla.orphanslife.data.datastore.RemoteDataSource

class DBRepo (private val remoteDataSource: RemoteDataSource){

    fun getAdminDetailsByID(apiToken: String, id: Int) = remoteDataSource.getAdminDetailsByID(apiToken, id)
    fun getSuccessPaymentCount(apiToken: String) = remoteDataSource.successPaymentCount(apiToken)
    fun getFailedPaymentsCount(apiToken: String) = remoteDataSource.failedPaymentsCount(apiToken)
    fun getNewAdoptReqsCount(apiToken: String) = remoteDataSource.newAdoptReqsCount(apiToken)
    fun getApprovedAdoptReqsCount(apiToken: String) = remoteDataSource.approvedAdoptReqsCount(apiToken)
    fun getRejectedAdoptReqsCount(apiToken: String) = remoteDataSource.rejectedAdoptReqsCount(apiToken)
    fun getMonthwiseDonationsTotal(apiToken: String) = remoteDataSource.monthwiseDonationsTotal(apiToken)
    fun getLocations(apiToken: String) = remoteDataSource.getLocations(apiToken)
    fun getOrphanages(apiToken: String) = remoteDataSource.getOrphanages(apiToken)
    fun getAdmins(apiToken: String) = remoteDataSource.getAdmins(apiToken)
}