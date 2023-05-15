package com.kaligotla.orphanslife.data.repository

import com.kaligotla.orphanslife.db.SponsorDao
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.entity.Sponsor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SponsorRepo @Inject constructor(private val sponsorDao: SponsorDao){

    suspend fun createSponsor(sponsor: Sponsor) : Long {
        return sponsorDao.addSponsor(sponsor)
    }

    val getSponsorDetails: Flow<List<Sponsor>> get() =  sponsorDao.getAllSponsors()

    suspend fun deleteSingleAdminRecord(id : Int) {
        sponsorDao.deleteSingleSponsor(id)
    }
}