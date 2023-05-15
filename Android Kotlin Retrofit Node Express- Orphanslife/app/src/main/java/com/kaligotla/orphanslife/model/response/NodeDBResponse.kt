package com.kaligotla.orphanslife.model.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class NodeDBResponse(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: List<JsonObject>
)


