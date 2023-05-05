package com.kaligotla.orphanslife.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "child"
)

data class Child(

    @PrimaryKey
    @SerializedName("child_id")
    val child_id: Int,

    @SerializedName("child_name")
    val child_name: String,

    @SerializedName("child_dob")
    val child_dob: String,

    @SerializedName("child_gender")
    val child_gender: String,

    @SerializedName("admitted_date")
    val admitted_date: String,

    @SerializedName("leave_date")
    val leave_date: String,

    @SerializedName("mother_name")
    val mother_name: String,

    @SerializedName("father_name")
    val father_name: String,

    @SerializedName("child_mobile")
    val child_mobile: String,

    @SerializedName("child_image")
    val child_image: String,

    @SerializedName("status_id")
    val status_id: Int,

    @SerializedName("admin_id")
    val admin_id: Int,

    @SerializedName("child_created_at")
    val child_created_at: String,

    @SerializedName("child_updated_at")
    val child_updated_at: String
)
