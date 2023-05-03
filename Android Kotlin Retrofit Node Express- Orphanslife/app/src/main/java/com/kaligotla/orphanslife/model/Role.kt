package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("role_id")
    val role_id: Int,

    @SerializedName("role")
    val role: String,

    @SerializedName("role_created_at")
    val role_created_at: String,

    @SerializedName("role_updated_at")
    val role_updated_at: String,
)
