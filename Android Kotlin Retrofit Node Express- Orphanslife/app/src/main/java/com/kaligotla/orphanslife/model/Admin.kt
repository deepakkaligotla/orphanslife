package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class Admin(
    @SerializedName("admin_id")
    val admin_id: Int,
    @SerializedName("admin_name")
    val admin_name: String,
    @SerializedName("admin_gender")
    val admin_gender: String,
    @SerializedName("admin_govt_id_type")
    val admin_govt_id_type: String,
    @SerializedName("admin_govt_id")
    val admin_govt_id: String,
    @SerializedName("admin_mobile")
    val admin_mobile: String,
    @SerializedName("admin_email")
    val admin_email: String,
    @SerializedName("admin_password")
    val admin_password: String,
    @SerializedName("admin_address")
    val admin_address: String,
    @SerializedName("admin_location")
    val admin_location: Location,
    @SerializedName("role")
    val role: Role,
    @SerializedName("admin_orphanage")
    val admin_orphanage: Orphanage,
    @SerializedName("admin_image")
    val admin_image: String,
    @SerializedName("admin_created_at")
    val admin_created_at: String,
    @SerializedName("admin_updated_at")
    val admin_updated_at: String
)