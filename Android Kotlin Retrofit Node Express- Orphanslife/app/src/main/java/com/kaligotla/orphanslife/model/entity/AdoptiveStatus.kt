package com.kaligotla.orphanslife.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "adoptive_status"
)

data class AdoptiveStatus(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("adoptive_status_id")
    val adoptive_status_id: Int,

    @SerializedName("status")
    val status: String
)
