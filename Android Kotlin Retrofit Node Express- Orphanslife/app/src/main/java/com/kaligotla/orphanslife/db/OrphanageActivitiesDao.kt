package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaligotla.orphanslife.model.entity.OrphanageActivities

@Dao
interface OrphanageActivitiesDao {

    @Query("select * from orphanage_activities;")
    fun getAllOrphanageActivities(): LiveData<List<OrphanageActivities>>
}