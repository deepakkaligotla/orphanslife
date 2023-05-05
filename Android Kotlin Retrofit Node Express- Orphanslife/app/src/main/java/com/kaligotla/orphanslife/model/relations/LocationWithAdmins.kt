package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.entity.Location

data class LocationWithAdmins(
    @Embedded val location: Location,

    @Relation(parentColumn = "location_id",
        entityColumn = "admin_location_id",
    )
    val admins: List<Admin>
)
