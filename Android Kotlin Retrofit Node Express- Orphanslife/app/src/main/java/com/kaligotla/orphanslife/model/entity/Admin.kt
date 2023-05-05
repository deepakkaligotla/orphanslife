package com.kaligotla.orphanslife.model.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.util.foreignKeyCheck
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "admin",
)

data class Admin(

    @PrimaryKey(autoGenerate = false)
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

    @SerializedName("admin_location_id")
    val admin_location_id: Int,

    @SerializedName("role_id")
    val role_id: Int,

    @SerializedName("admin_orphanage_id")
    val admin_orphanage_id: Int,

    @SerializedName("admin_image")
    val admin_image: String,

    @SerializedName("admin_created_at")
    val admin_created_at: String,

    @SerializedName("admin_updated_at")
    val admin_updated_at: String,
)