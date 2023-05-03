package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("location_id")
    val location_id: Int,

    @SerializedName("pincode")
    val pincode: Int,

    @SerializedName("area")
    val area: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("district")
    val district: String,

    @SerializedName("state")
    val state: String
)
