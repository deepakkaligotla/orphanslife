package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Location
import com.kaligotla.orphanslife.model.entity.Sponsor

data class LocationWithSponsors(
    @Embedded val location: Location,

    @Relation(parentColumn = "location_id",
        entityColumn = "sponsor_location_id",
    )
    val sponsors: List<Sponsor>
)
