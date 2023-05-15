package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.entity.Sponsor
import com.kaligotla.orphanslife.model.relations.DonationWithSponsors
import com.kaligotla.orphanslife.model.relations.LocationWithAdmins
import com.kaligotla.orphanslife.model.relations.LocationWithSponsors
import com.kaligotla.orphanslife.model.relations.RoleWithAdmins
import kotlinx.coroutines.flow.Flow

@Dao
interface SponsorDao {

    @Query("select * from sponsor;")
    fun getAllSponsors(): Flow<List<Sponsor>>

    @Query("select * from sponsor where sponsor_id=:id;")
    fun getSponsor(id: Int): Sponsor

    @Transaction
    @Query("SELECT * FROM sponsor JOIN location on sponsor.sponsor_location_id = location.location_id;")
    suspend fun getLocationWithSponsors(): List<LocationWithSponsors>

    @Transaction
    @Query("SELECT * FROM sponsor JOIN donation on sponsor.donation_id = donation.donation_id;")
    suspend fun getDonationWithSponsors(): List<DonationWithSponsors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSponsor(sponsor: Sponsor): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSponsor(sponsor: Sponsor)

    @Query("DELETE FROM sponsor WHERE sponsor_id = :id")
    suspend fun deleteSingleSponsor(id: Int)

    @Delete
    suspend fun deleteAllSponsors(admin: Admin)
}