package com.kaligotla.orphanslife.data.repository

import com.kaligotla.orphanslife.db.AdminDao
import com.kaligotla.orphanslife.model.entity.Admin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdminRepo @Inject constructor(private val adminDao: AdminDao){

    suspend fun createAdmin(admin: Admin) : Long {
        return adminDao.addAdmin(admin)
    }

    val getAdminDetails: Flow<List<Admin>> get() =  adminDao.getAllAdmins()

    suspend fun deleteSingleAdminRecord(id : Int) {
        adminDao.deleteSingleAdmin(id)
    }
}