package com.kaligotla.orphanslife.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "adopt_req"
)

data class AdoptReq(

    @PrimaryKey
    @SerializedName("adop_req_id")
    val adop_req_id: Int,

    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("admin_id")
    val admin_id: Int,

    @SerializedName("child_id")
    val child_id: Int,

    @SerializedName("reason")
    val reason: String,

    @SerializedName("req_stage")
    val req_stage: String,

    @SerializedName("date_of_req")
    val date_of_req: String,

    @SerializedName("last_checked")
    val last_checked: String,

    @SerializedName("req_comment")
    val req_comment: String,

    @SerializedName("next_check")
    val next_check: String,

    @SerializedName("adopted")
    val adopted: String
)
