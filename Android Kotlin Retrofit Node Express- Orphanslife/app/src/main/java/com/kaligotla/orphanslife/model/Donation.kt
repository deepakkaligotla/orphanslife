package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class Donation(
    @SerializedName("donation_id")
    val donation_id: Int,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("payment_status")
    val payment_status: String,

    @SerializedName("donation_created_at")
    val donation_created_at: String,

    @SerializedName("donation_updated_at")
    val donation_updated_at: String
)
