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
    public Call<JsonObject> register(@Body Sponsor sponsor);

    @POST("findByEmailSponsor")
    public Call<JsonObject> checkSponsorExists(@Body Sponsor sponsor);

    @POST("newrole")
    public Call<JsonObject> newRole(@Body Role role);

    @POST("newadoptreq")
    public Call<JsonObject> newAdoptReq(@Body AdoptRequest newAdoptReq);

    @PUT("updatesponsorbyid/{sid}")
    public Call<JsonObject> updateSponsor(@Path("sid") int sid, @Body Sponsor updateSponsor);
    @PUT("updateadminbyid/{aid}")
    public Call<JsonObject> updateAdmin(@Path("aid") int aid, @Body Admin updateAdmin);

    @PUT("updateadoptreqbyid/{req_no}")
    public Call<JsonObject> updateAdoptReq(@Path("req_no") int aid, @Body AdoptRequest adoptRequest);

    @POST("forgotpasswordsendotp")
    public Call<JsonObject> forgotPasswordSendOTP(@Body Cred cred);

    @PUT("updatesponsorpassword")
    public Call<JsonObject> changePassword(@Body Cred cred);

    @POST("/")
    public Call<JsonObject> login(@Body Cred cred);

    @GET("sponsors")
    public Call<JsonObject> sponsors();

    @GET("success_payments")
    public Call<JsonObject> getSuccessPaymentsCount();

    @GET("failed_payments")
    public Call<JsonObject> getFailedPaymentsCount();

    @GET("new_adopt_reqs")
    public Call<JsonObject> getNewAdoptReqsCount();

    @GET("approved_adopt_reqs")
    public Call<JsonObject> getApprovedAdoptReqsCount();

    @GET("rejected_adopt_reqs")
    public Call<JsonObject> getRejectedAdoptReqsCount();

    @GET("monthwise_donations")
    public Call<JsonObject> getMonthwiseDonationsTotal();

    @POST("locations")
    public Call<JsonObject> locations(@Body Pagination pagination);

    @GET("findByIdSponsor/{id}")
    public Call<JsonObject> getSponsorByID(@Path("id") int id);

    @DELETE("deleteadmin/{id}")
    public Call<JsonObject> deleteAdminByID(@Header("x-auth-token") String apiToken, @Path("id") int id);

    @DELETE("deleteAdoptReqById/{id}")
    public Call<JsonObject> deleteAdoptReqByID(@Path("id") int id);

    @DELETE("deleteAdoptiveStatusById/{id}")
    public Call<JsonObject> deleteAdoptiveStatusByID(@Path("id") int id);

    @DELETE("deleteSponsor/{id}")
    public Call<JsonObject> deleteSponsorByID(@Path("id") int id);

    @DELETE("deletechild/{id}")
    public Call<JsonObject> deleteChildByID(@Path("id") int id);

    @DELETE("deleterole/{id}")
    public Call<JsonObject> deleteRoleByID(@Path("id") int id);

    @GET("admins")
    public Call<JsonObject> admins(@Header("x-auth-token") String apiToken);

    @GET("childs")
    public Call<JsonObject> childs();

    @GET("roles")
    public Call<JsonObject> roles();

    @GET("orphanages")
    public Call<JsonObject> orphanages();

    @GET("adoptstatus")
    public Call<JsonObject> adoptstatus();

    @GET("activities")
    public Call<JsonObject> orphanageActivities();

    @GET("donations")
    public Call<JsonObject> donations();

    @GET("adoptrequests")
    public Call<JsonObject> adoptRequests();

    @GET("findByIdAdmin/{id}")
    public Call<JsonObject> getAdminByID(@Path("id") int id);

    @GET("findByIdAdoptRequest/{id}")
    public Call<JsonObject> getAdoptRequestByID(@Path("id") int id);

    @POST("newOrphanageActivity")
    public Call<JsonObject> newOrphanageActivity(@Body OrphanageActivities orphanageActivities);

    @GET("findByIdRole/{id}")
    public Call<JsonObject> getRoleByID(@Path("id") int id);

    @GET("findByIdChild/{id}")
    public Call<JsonObject> getChildByID(@Path("id") int id);

    @GET("findByIdOrphanage/{id}")
    public Call<JsonObject> getOrphanageByID(@Path("id") int id);

    @GET("findByIdAdoptStatus/{id}")
    public Call<JsonObject> getAdoptStatusByID(@Path("id") int id);

    @GET("findByIdOrphanageActivities/{id}")
    public Call<JsonObject> getOrphanageActivitiesByID(@Path("id") int id);

    @GET("findByIdLocation/{location_id}")
    public Call<JsonObject> getLocationByID(@Path("location_id") int location_id);

    @GET("findByIdDonation/{donation_id}")
    public Call<JsonObject> getDonationsByID(@Path("donation_id") int donation_id);
}
