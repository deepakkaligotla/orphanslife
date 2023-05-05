package com.kaligotla.orphanslife.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "role"
)

data class Role(

    @PrimaryKey
    @SerializedName("role_id")
    val role_id: Int,

    @SerializedName("role")
    val role: String,

    @SerializedName("role_created_at")
    val role_created_at: String,

    @SerializedName("role_updated_at")
    val role_updated_at: String
)
