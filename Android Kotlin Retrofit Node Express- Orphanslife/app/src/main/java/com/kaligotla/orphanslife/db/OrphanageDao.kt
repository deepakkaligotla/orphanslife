package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kaligotla.orphanslife.model.entity.Orphanage
import com.kaligotla.orphanslife.model.relations.LocationWithOrphanages

@Dao
interface OrphanageDao {

    @Query("select * from orphanage;")
    fun getAllOrphanages(): LiveData<List<Orphanage>>

    @Transaction
    @Query("SELECT * FROM orphanage INNER JOIN location on location.location_id = orphanage.orphanage_location_id;")
    suspend fun getLocationWithOrphanages(): List<LocationWithOrphanages>
}