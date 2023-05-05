package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaligotla.orphanslife.model.entity.Sponsor

@Dao
interface SponsorDao {

    @Query("select * from sponsor;")
    fun getAllSponsors(): LiveData<List<Sponsor>>
}