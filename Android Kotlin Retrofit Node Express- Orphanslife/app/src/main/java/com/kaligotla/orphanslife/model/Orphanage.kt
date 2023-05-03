package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class Orphanage(
    @SerializedName("orphanage_id")
    val orphanage_id: Int,

    @SerializedName("type")
    val type: String,

    @SerializedName("orphanage_address")
    val orphanage_address: String,

    @SerializedName("orphanage_location")
    val orphanage_location: Location,

    @SerializedName("contact_person")
    val contact_person: String,

    @SerializedName("orphanage_mobile1")
    val orphanage_mobile1: String,

    @SerializedName("orphanage_mobile2")
    val orphanage_mobile2: String,

    @SerializedName("orphanage_email")
    val orphanage_email: String,

    @SerializedName("in_home")
    val in_home: Int,

    @SerializedName("adoptable")
    val adoptable: Int,

    @SerializedName("boys")
    val boys: Int,

    @SerializedName("girls")
    val girls: Int,

    @SerializedName("orphanage_image")
    val orphanage_image: String,

    @SerializedName("orphanage_created_at")
    val orphanage_created_at: String,

    @SerializedName("orphanage_updated_at")
    val orphanage_updated_at: String
)
