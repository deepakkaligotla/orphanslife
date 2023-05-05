package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.entity.Child

data class AdminWithChilds(
    @Embedded val admin: Admin,

    @Relation(parentColumn = "admin_id",
        entityColumn = "admin_id",
    )
    val childs: List<Child>
)
