package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Location
import com.kaligotla.orphanslife.model.entity.Orphanage

data class LocationWithOrphanages (
    @Embedded val location: Location,

    @Relation(parentColumn = "location_id",
        entityColumn = "orphanage_location_id",
    )
    val orphanages: List<Orphanage>
)