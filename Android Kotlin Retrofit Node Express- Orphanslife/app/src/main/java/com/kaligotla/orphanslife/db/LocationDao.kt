package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kaligotla.orphanslife.model.entity.Location
import com.kaligotla.orphanslife.model.relations.LocationWithAdmins
import com.kaligotla.orphanslife.model.relations.LocationWithOrphanages
import com.kaligotla.orphanslife.model.relations.LocationWithSponsors

@Dao
interface LocationDao {

    @Query("select * from location")
    fun getAllLocations(): LiveData<List<Location>>

    @Transaction
    @Query("SELECT * FROM location JOIN orphanage on location.location_id = orphanage.orphanage_location_id;")
    suspend fun getLocationWithOrphanages(): List<LocationWithOrphanages>

    @Transaction
    @Query("SELECT * FROM location JOIN admin on location.location_id = admin.admin_location_id;")
    suspend fun getLocationWithAdmins(): List<LocationWithAdmins>

    @Transaction
    @Query("SELECT * FROM location JOIN sponsor on location.location_id = sponsor.sponsor_location_id;")
    suspend fun getLocationWithSponsors(): List<LocationWithSponsors>
}