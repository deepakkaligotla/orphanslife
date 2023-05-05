package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaligotla.orphanslife.model.entity.AdoptiveStatus

@Dao
interface AdoptiveStatusDao {

    @Query("select * from adoptive_status;")
    fun getAllAdoptiveStatus(): LiveData<List<AdoptiveStatus>>
}