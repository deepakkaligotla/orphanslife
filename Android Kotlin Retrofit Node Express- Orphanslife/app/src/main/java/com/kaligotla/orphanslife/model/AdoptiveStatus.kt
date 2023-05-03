package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class AdoptiveStatus(
    @SerializedName("adoptive_status_id")
    val adoptive_status_id: Int,

    @SerializedName("status")
    val status: String
)
