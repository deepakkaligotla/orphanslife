package com.kaligotla.orphanslife.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.entity.Location
import com.kaligotla.orphanslife.model.entity.Role

data class RoleWithAdmins(
    @Embedded val role: Role,

    @Relation(parentColumn = "role_id", entityColumn = "role_id")
    val admins: List<Admin>
)
