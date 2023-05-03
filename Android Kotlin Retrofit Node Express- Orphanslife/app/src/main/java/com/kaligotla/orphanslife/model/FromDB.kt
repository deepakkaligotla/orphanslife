package com.kaligotla.orphanslife.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class FromDB(
    @SerializedName("otp")
    var otp: String? = "",

    @SerializedName("token")
    var token: String? = "",

    @SerializedName("loggedInUser")
    var loggedInUser: JsonArray? = null

)
