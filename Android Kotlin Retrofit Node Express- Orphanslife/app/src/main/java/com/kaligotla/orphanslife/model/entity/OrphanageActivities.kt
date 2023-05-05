package com.kaligotla.orphanslife.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "orphanage_activities"
)

data class OrphanageActivities(

    @PrimaryKey
    @SerializedName("event_id")
    val event_id: Int,

    @SerializedName("orphanage_id")
    val orphanage_id: Int,

    @SerializedName("details")
    val details: String,

    @SerializedName("image_1")
    val image_1: String,

    @SerializedName("image_2")
    val image_2: String,

    @SerializedName("image_3")
    val image_3: String,

    @SerializedName("image_4")
    val image_4: String,

    @SerializedName("image_5")
    val image_5: String,
)
