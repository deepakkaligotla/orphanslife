package com.kaligotla.orphanslife.model.entity

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "location"
)

data class Location(

    @PrimaryKey
    @SerializedName("location_id")
    @ColumnInfo(name = "location_id")
    val location_id: Int,

    @NonNull
    @ColumnInfo(name = "pincode")
    @SerializedName("pincode")
    val pincode: Int,

    @NonNull
    @ColumnInfo(name = "area")
    @SerializedName("area")
    val area: String,

    @NonNull
    @ColumnInfo(name = "city")
    @SerializedName("city")
    val city: String,

    @NonNull
    @ColumnInfo(name = "district")
    @SerializedName("district")
    val district: String,

    @NonNull
    @ColumnInfo(name = "state")
    @SerializedName("state")
    val state: String,
)
