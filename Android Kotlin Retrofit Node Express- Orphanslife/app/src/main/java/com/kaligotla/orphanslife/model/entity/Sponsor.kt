package com.kaligotla.orphanslife.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "sponsor"
)

data class Sponsor(

    @PrimaryKey
    @SerializedName("sponsor_id")
    val sponsor_id: Int,

    @SerializedName("sponsor_name")
    val sponsor_name: String,

    @SerializedName("sponsor_dob")
    val sponsor_dob: String,

    @SerializedName("sponsor_gender")
    val sponsor_gender: String,

    @SerializedName("sponsor_govt_id_type")
    val sponsor_govt_id_type: String,

    @SerializedName("sponsor_govt_id")
    val sponsor_govt_id: String,

    @SerializedName("sponsor_mobile")
    val sponsor_mobile: String,

    @SerializedName("sponsor_email")
    val sponsor_email: String,

    @SerializedName("sponsor_password")
    val sponsor_password: String,

    @SerializedName("marital_status")
    val marital_status: String,

    @SerializedName("sponsor_image")
    val sponsor_image: String,

    @SerializedName("sponsor_address")
    val sponsor_address: String,

    @SerializedName("sponsor_location_id")
    val sponsor_location_id: Int,

    @SerializedName("spouce_name")
    val spouce_name: String,

    @SerializedName("spouce_dob")
    val spouce_dob: String,

    @SerializedName("spouce_govt_id_type")
    val spouce_govt_id_type: String,

    @SerializedName("spouce_govt_id")
    val spouce_govt_id: String,

    @SerializedName("spouce_mobile")
    val spouce_mobile: String,

    @SerializedName("spouce_image")
    val spouce_image: String,

    @SerializedName("donation_id")
    val donation_id: Int,

    @SerializedName("sponsor_created_at")
    val sponsor_created_at: String,

    @SerializedName("sponsor_updated_at")
    val sponsor_updated_at: String
)
