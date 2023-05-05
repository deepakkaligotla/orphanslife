package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kaligotla.orphanslife.model.entity.Child
import com.kaligotla.orphanslife.model.relations.AdminWithChilds
import com.kaligotla.orphanslife.model.relations.AdoptiveStatusWithChilds
import com.kaligotla.orphanslife.model.relations.LocationWithAdmins

@Dao
interface ChildDao {

    @Query("select * from child;")
    fun getAllChilds(): LiveData<List<Child>>

    @Transaction
    @Query("SELECT * FROM child INNER JOIN adoptive_status on child.status_id = adoptive_status.adoptive_status_id;")
    suspend fun getAdoptiveStatusWithChilds(): List<AdoptiveStatusWithChilds>

    @Transaction
    @Query("SELECT * FROM child INNER JOIN admin on child.admin_id = admin.admin_id;")
    suspend fun getAdminWithChilds(): List<AdminWithChilds>
}