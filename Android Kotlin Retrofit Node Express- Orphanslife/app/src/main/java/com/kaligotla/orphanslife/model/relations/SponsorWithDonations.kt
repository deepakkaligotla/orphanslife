package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Donation
import com.kaligotla.orphanslife.model.entity.Sponsor

data class SponsorWithDonations(
    @Embedded val sponsor: Sponsor,

    @Relation(parentColumn = "sponsor_id",
        entityColumn = "sponsor_id",
    )
    val donations: List<Donation>
)
