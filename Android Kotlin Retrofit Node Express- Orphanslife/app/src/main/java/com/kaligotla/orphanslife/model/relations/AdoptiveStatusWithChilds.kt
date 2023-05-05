package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.AdoptiveStatus
import com.kaligotla.orphanslife.model.entity.Child

data class AdoptiveStatusWithChilds(
    @Embedded val adoptiveStatus: AdoptiveStatus,

    @Relation(parentColumn = "adoptive_status_id",
        entityColumn = "status_id",
    )
    val childs: List<Child>
)
