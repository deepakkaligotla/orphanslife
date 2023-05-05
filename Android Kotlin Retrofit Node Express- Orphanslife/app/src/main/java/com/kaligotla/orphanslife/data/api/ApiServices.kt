package com.kaligotla.orphanslife.data.api

import com.kaligotla.orphanslife.model.response.LoginResponse
import com.kaligotla.orphanslife.model.response.LoginBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @Headers("Content-Type: application/json")
    @POST("/")
    fun login(
        @Body loginBody : LoginBody
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/admins")
    fun admins(
        @Header("x-auth-token") apiToken : String
    ): Call<LoginResponse>
}