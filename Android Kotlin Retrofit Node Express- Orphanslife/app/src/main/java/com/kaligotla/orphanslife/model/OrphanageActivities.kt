package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class OrphanageActivities(
    @SerializedName("event_id")
    val event_id: Int,

    @SerializedName("orphanage")
    val orphanage: Orphanage,

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
