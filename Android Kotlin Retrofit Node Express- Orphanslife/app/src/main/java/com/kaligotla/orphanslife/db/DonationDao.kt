package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kaligotla.orphanslife.model.entity.Donation
import com.kaligotla.orphanslife.model.relations.LocationWithAdmins
import com.kaligotla.orphanslife.model.relations.SponsorWithDonations

@Dao
interface DonationDao {

    @Query("select * from donation;")
    fun getAllDonations(): LiveData<List<Donation>>

    @Transaction
    @Query("SELECT * FROM donation INNER JOIN sponsor on donation.sponsor_id = sponsor.sponsor_id;")
    suspend fun getSponsorWithDonations(): List<SponsorWithDonations>
}