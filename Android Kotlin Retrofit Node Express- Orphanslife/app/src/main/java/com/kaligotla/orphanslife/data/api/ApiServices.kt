package com.kaligotla.orphanslife.data.api

import com.kaligotla.orphanslife.model.LoginResponse
import com.kaligotla.orphanslife.model.LoginBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @Headers("Content-Type: application/json")
    @POST("/")
    fun login(
        @Body loginBody : LoginBody
    ): Call<LoginResponse>
}