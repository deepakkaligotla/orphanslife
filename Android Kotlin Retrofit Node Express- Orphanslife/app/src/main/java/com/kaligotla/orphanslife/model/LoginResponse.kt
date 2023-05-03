package com.kaligotla.orphanslife.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("otp")
	val otp: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("data")
	val data: List<LoginResult>
)

data class LoginResult(

	@field:SerializedName("loggedInUser")
	val loggedInUser: JsonObject
)
