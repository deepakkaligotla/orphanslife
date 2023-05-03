package com.kaligotla.orphanslife.model

import com.google.gson.annotations.SerializedName

data class AdoptReq(

    @SerializedName("adop_req_id")
    val adop_req_id: Int,

    @SerializedName("user_id")
    val user_id: Sponsor,

    @SerializedName("admin_id")
    val admin_id: Admin,

    @SerializedName("child_id")
    val child_id: Child,

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
