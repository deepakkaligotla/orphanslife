package com.kaligotla.orphanslife.model.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "donation"
)

data class Donation(

    @PrimaryKey
    @SerializedName("donation_id")
    val donation_id: Int,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("payment_status")
    val payment_status: String,

    @SerializedName("sponsor_id")
    val sponsor_id: Int,

    @SerializedName("donation_created_at")
    val donation_created_at: String,

    @SerializedName("donation_updated_at")
    val donation_updated_at: String
)
