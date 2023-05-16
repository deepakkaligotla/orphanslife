package com.kaligotla.orphanslife.data.api

import com.google.gson.JsonObject
import com.kaligotla.orphanslife.model.response.LoginBody
import com.kaligotla.orphanslife.model.response.LoginResponse
import com.kaligotla.orphanslife.model.response.NodeDBResponse
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
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("/findByIdAdmin/{id}")
    fun getAdminDetailsByID (
        @Header("x-auth-token") apiToken : String,
        @Path("id") id : Int
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("/success_payments")
    fun getSuccessPaymentCount (
        @Header("x-auth-token") apiToken : String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("/failed_payments")
    fun getFailedPaymentsCount (
        @Header("x-auth-token") apiToken : String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("new_adopt_reqs")
    fun getNewAdoptReqsCount(
        @Header("x-auth-token") apiToken: String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("approved_adopt_reqs")
    fun getApprovedAdoptReqsCount(
        @Header("x-auth-token") apiToken: String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("rejected_adopt_reqs")
    fun getRejectedAdoptReqsCount(
        @Header("x-auth-token") apiToken: String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("monthwise_donations")
    fun getMonthwiseDonationsTotal(
        @Header("x-auth-token") apiToken: String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("locations")
    fun locations(
        @Header("x-auth-token") apiToken: String
    ): Call<NodeDBResponse>

    @Headers("Content-Type: application/json")
    @GET("orphanages")
    fun orphanages(
        @Header("x-auth-token") apiToken: String
    ): Call<NodeDBResponse>
}