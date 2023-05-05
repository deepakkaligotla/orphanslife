package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.relations.AdminsWithOrphanages
import com.kaligotla.orphanslife.model.relations.LocationWithAdmins
import com.kaligotla.orphanslife.model.relations.RoleWithAdmins
import kotlinx.coroutines.flow.Flow

@Dao
interface AdminDao {

    @Query("select * from admin;")
    fun getAllAdmins(): Flow<List<Admin>>

    @Query("select * from admin where admin_id=:id;")
    fun getAdmin(id: Int): Admin

    @Transaction
    @Query("SELECT * FROM admin JOIN location on admin.admin_location_id = location.location_id;")
    suspend fun getLocationWithAdmins(): List<LocationWithAdmins>

    @Transaction
    @Query("SELECT * FROM admin JOIN role on admin.role_id = role.role_id;")
    suspend fun getRoleWithAdmins(): List<RoleWithAdmins>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAdmin(admin: Admin): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAdmin(admin: Admin)

    @Query("DELETE FROM admin WHERE admin_id = :id")
    suspend fun deleteSingleAdmin(id: Int)

    @Delete
    suspend fun deleteAllAdmins(admin: Admin)
}