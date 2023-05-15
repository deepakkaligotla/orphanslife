package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Donation
import com.kaligotla.orphanslife.model.entity.Sponsor

data class DonationWithSponsors(

    @Embedded val donation: Donation,

    @Relation(parentColumn = "donation_id",
        entityColumn = "donation_id",
    )
    val admins: List<Sponsor>
)