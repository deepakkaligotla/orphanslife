package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaligotla.orphanslife.model.entity.Role

@Dao
interface RoleDao {

    @Query("select * from role;")
    fun getAllRoles(): LiveData<List<Role>>
}