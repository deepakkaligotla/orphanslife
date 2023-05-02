package com.kaligotla.oms.Essentials;

import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.AdoptRequest.AdoptRequest;
import com.kaligotla.oms.AdminView.Location.Pagination;
import com.kaligotla.oms.AdminView.Role.Role;
import com.kaligotla.oms.Cred;
import com.kaligotla.oms.OrphanageActivities.OrphanageActivities;
import com.kaligotla.oms.SponsorView.Sponsor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DBService {

    @POST("newsponsor")
    public Call<JsonObject> register(@Header("x-auth-token") String apiToken, @Body Sponsor sponsor);

    @POST("findByEmailSponsor")
    public Call<JsonObject> checkSponsorExists(@Header("x-auth-token") String apiToken, @Body Sponsor sponsor);

    @POST("newrole")
    public Call<JsonObject> newRole(@Header("x-auth-token") String apiToken, @Body Role role);

    @POST("newadoptreq")
    public Call<JsonObject> newAdoptReq(@Header("x-auth-token") String apiToken, @Body AdoptRequest newAdoptReq);

    @PUT("updatesponsorbyid/{sid}")
    public Call<JsonObject> updateSponsor(@Header("x-auth-token") String apiToken, @Path("sid") int sid, @Body Sponsor updateSponsor);
    @PUT("updateadminbyid/{aid}")
    public Call<JsonObject> updateAdmin(@Header("x-auth-token") String apiToken, @Path("aid") int aid, @Body Admin updateAdmin);

    @PUT("updateadoptreqbyid/{req_no}")
    public Call<JsonObject> updateAdoptReq(@Header("x-auth-token") String apiToken, @Path("req_no") int aid, @Body AdoptRequest adoptRequest);

    @POST("forgotpasswordsendotp")
    public Call<JsonObject> forgotPasswordSendOTP(@Header("x-auth-token") String apiToken, @Body Cred cred);

    @PUT("updatesponsorpassword")
    public Call<JsonObject> changePassword(@Header("x-auth-token") String apiToken, @Body Cred cred);

    @GET("/")
    public Call<JsonObject> userMachine(@Body Cred cred);

    @POST("/")
    public Call<JsonObject> login(@Body Cred cred);

    @GET("sponsors")
    public Call<JsonObject> sponsors(@Header("x-auth-token") String apiToken);

    @GET("success_payments")
    public Call<JsonObject> getSuccessPaymentsCount(@Header("x-auth-token") String apiToken);

    @GET("failed_payments")
    public Call<JsonObject> getFailedPaymentsCount(@Header("x-auth-token") String apiToken);

    @GET("new_adopt_reqs")
    public Call<JsonObject> getNewAdoptReqsCount(@Header("x-auth-token") String apiToken);

    @GET("approved_adopt_reqs")
    public Call<JsonObject> getApprovedAdoptReqsCount(@Header("x-auth-token") String apiToken);

    @GET("rejected_adopt_reqs")
    public Call<JsonObject> getRejectedAdoptReqsCount(@Header("x-auth-token") String apiToken);

    @GET("monthwise_donations")
    public Call<JsonObject> getMonthwiseDonationsTotal(@Header("x-auth-token") String apiToken);

    @POST("locations")
    public Call<JsonObject> locations(@Header("x-auth-token") String apiToken, @Body Pagination pagination);

    @GET("findByIdSponsor/{id}")
    public Call<JsonObject> getSponsorByID(@Header("x-auth-token") @Path("id") int id);

    @DELETE("deleteadmin/{id}")
    public Call<JsonObject> deleteAdminByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @DELETE("deleteAdoptReqById/{id}")
    public Call<JsonObject> deleteAdoptReqByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @DELETE("deleteAdoptiveStatusById/{id}")
    public Call<JsonObject> deleteAdoptiveStatusByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @DELETE("deleteSponsor/{id}")
    public Call<JsonObject> deleteSponsorByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @DELETE("deletechild/{id}")
    public Call<JsonObject> deleteChildByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @DELETE("deleterole/{id}")
    public Call<JsonObject> deleteRoleByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("admins")
    public Call<JsonObject> admins(@Header("x-auth-token") String apiToken);

    @GET("childs")
    public Call<JsonObject> childs(@Header("x-auth-token") String apiToken);

    @GET("roles")
    public Call<JsonObject> roles(@Header("x-auth-token") String apiToken);

    @GET("orphanages")
    public Call<JsonObject> orphanages(@Header("x-auth-token") String apiToken);

    @GET("adoptstatus")
    public Call<JsonObject> adoptstatus(@Header("x-auth-token") String apiToken);

    @GET("activities")
    public Call<JsonObject> orphanageActivities(@Header("x-auth-token") String apiToken);

    @GET("donations")
    public Call<JsonObject> donations(@Header("x-auth-token") String apiToken);

    @GET("adoptrequests")
    public Call<JsonObject> adoptRequests(@Header("x-auth-token") String apiToken);

    @GET("findByIdAdmin/{id}")
    public Call<JsonObject> getAdminByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("findByIdAdoptRequest/{id}")
    public Call<JsonObject> getAdoptRequestByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @POST("newOrphanageActivity")
    public Call<JsonObject> newOrphanageActivity(@Header("x-auth-token") String apiToken, @Body OrphanageActivities orphanageActivities);

    @GET("findByIdRole/{id}")
    public Call<JsonObject> getRoleByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("findByIdChild/{id}")
    public Call<JsonObject> getChildByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("findByIdOrphanage/{id}")
    public Call<JsonObject> getOrphanageByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("findByIdAdoptStatus/{id}")
    public Call<JsonObject> getAdoptStatusByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("findByIdOrphanageActivities/{id}")
    public Call<JsonObject> getOrphanageActivitiesByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @GET("findByIdLocation/{location_id}")
    public Call<JsonObject> getLocationByID(@Header("x-auth-token") String apiToken, @Path("location_id") int location_id);

    @GET("findByIdDonation/{donation_id}")
    public Call<JsonObject> getDonationsByID(@Header("x-auth-token") String apiToken,@Path("donation_id") int donation_id);
}
