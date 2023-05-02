package com.kaligotla.oms.AdminView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.AdminsTable;
import com.kaligotla.oms.AdminView.AdoptRequest.AdoptRequestsTable;
import com.kaligotla.oms.AdminView.AdoptiveStatus.AdoptiveStatusTable;
import com.kaligotla.oms.AdminView.Child.ChildsTable;
import com.kaligotla.oms.AdminView.Location.LocationsTable;
import com.kaligotla.oms.AdminView.Role.RolesTable;
import com.kaligotla.oms.AdminView.Sponsor.SponsorsTable;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.OrphanageActivities.OrphanageActivitiesTable;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.SponsorHome;
import com.kaligotla.oms.orphanage.OrphanagesTable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SetJavaScriptEnabled")
public class AdminHome extends AppCompatActivity {
    Toolbar sponsor_toolbar;
    WebView donationDetailsWebView, adoptRequestDetailsWebView, monthwiseDonationDetailsWebView;
    int success, failed, new_req, approved, rejected;
    double[] monthwise_donations;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        sponsor_toolbar = findViewById(R.id.sponsor_toolbar);
        donationDetailsWebView = findViewById(R.id.donationDetailsWebView);
        adoptRequestDetailsWebView = findViewById(R.id.adoptRequestDetailsWebView);
        monthwiseDonationDetailsWebView = findViewById(R.id.monthwiseDonationDetailsWebView);
    }

    @Override
    protected void onResume() {
        setSupportActionBar(sponsor_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onResume();
        Log.e("API Token", this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""));
        monthwise_donations = new double[12];

        donationPaymentStatusDashboardThread();
        monthwiseDonationsDashboardThread();
        adoptRequestsDashboardThread();
    }

    public void getSuccessPaymentsCount() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .getSuccessPaymentsCount(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response!=null) {
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            JsonObject jsonObject;
                            if (!jsonArray.isJsonNull()) {
                                jsonObject = jsonArray.get(0).getAsJsonObject();
                                success = jsonObject.get("success_payments").getAsInt();
                            } else if (jsonArray.isJsonNull()) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("success", "" + success);
                        } else if (response==null) {
                            Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getFailedPaymentsCount() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .getFailedPaymentsCount(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response!=null) {
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            JsonObject jsonObject;
                            if (!jsonArray.isJsonNull()) {
                                jsonObject = jsonArray.get(0).getAsJsonObject();
                                failed = jsonObject.get("failed_payments").getAsInt();
                            } else if (jsonArray.isJsonNull()) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("failed", "" + failed);
                        } else if (response==null) {
                            Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void donationPaymentStatusDashboardThread() {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String donationDetails = "file:///android_asset/donation_payment_details.html";
                        donationDetailsWebView.loadUrl(donationDetails);
                        donationDetailsWebView.addJavascriptInterface(new WebAppInterface(), "Android");
                        donationDetailsWebView.getSettings().setJavaScriptEnabled(true);
                        getSuccessPaymentsCount();
                        getFailedPaymentsCount();
                    }
                });
            }
        }).start();
    }

    public void getNewAdoptReqsCount() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .getNewAdoptReqsCount(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response!=null) {
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            JsonObject jsonObject;
                            if (!jsonArray.isJsonNull()) {
                                jsonObject = jsonArray.get(0).getAsJsonObject();
                                new_req = jsonObject.get("new_req").getAsInt();
                            } else if (jsonArray.isJsonNull()) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("new_req", "" + new_req);
                        } else if (response==null) {
                            Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getApprovedAdoptReqsCount() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .getApprovedAdoptReqsCount(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response!=null) {
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            JsonObject jsonObject;
                            if (!jsonArray.isJsonNull()) {
                                jsonObject = jsonArray.get(0).getAsJsonObject();
                                approved = jsonObject.get("approved").getAsInt();
                            } else if (jsonArray.isJsonNull()) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("approved", "" + approved);
                        } else if (response==null) {
                            Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getRejectedAdoptReqsCount() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .getRejectedAdoptReqsCount(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response!=null) {
                            if (!response.body().isJsonNull()) {
                                JsonArray jsonArray = response.body().getAsJsonArray("data");
                                JsonObject jsonObject;
                                if (!jsonArray.isJsonNull()) {
                                    jsonObject = jsonArray.get(0).getAsJsonObject();
                                    rejected = jsonObject.get("rejected").getAsInt();
                                } else if (jsonArray.isJsonNull()) {
                                    Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                                }
                                Log.e("rejected", "" + rejected);
                            } else if (response.body().isJsonNull()) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            }
                        } else if (response==null) {
                            Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void adoptRequestsDashboardThread() {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String adoptRequestDetails = "file:///android_asset/adopt_request_details.html";
                        adoptRequestDetailsWebView.loadUrl(adoptRequestDetails);
                        adoptRequestDetailsWebView.addJavascriptInterface(new WebAppInterface(), "Android");
                        adoptRequestDetailsWebView.getSettings().setJavaScriptEnabled(true);
                        getNewAdoptReqsCount();
                        getApprovedAdoptReqsCount();
                        getRejectedAdoptReqsCount();
                    }
                });
            }
        }).start();
    }

    public void getMonthwiseDonationsTotal() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .getMonthwiseDonationsTotal(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response!=null) {
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            JsonObject jsonObject;
                            Log.e("jsonArray", "" + jsonArray);
                            if (!jsonArray.isJsonNull()) {
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    jsonObject = jsonArray.get(i).getAsJsonObject();
                                    //January
                                    if (jsonObject.get("Month").equals("January")) {
                                        monthwise_donations[0] = jsonObject.get("donations").getAsDouble();
                                        Log.e("January", "" + monthwise_donations[0]);
                                    }
                                    //February
                                    if (jsonObject.get("Month").getAsString().equals("February")) {
                                        monthwise_donations[1] = jsonObject.get("donations").getAsDouble();
                                        Log.e("February", "" + monthwise_donations[1]);
                                    }
                                    //March
                                    if (jsonObject.get("Month").getAsString().equals("March")) {
                                        monthwise_donations[2] = jsonObject.get("donations").getAsDouble();
                                        Log.e("March", "" + monthwise_donations[2]);
                                    }
                                    //April
                                    if (jsonObject.get("Month").getAsString().equals("April")) {
                                        monthwise_donations[3] = jsonObject.get("donations").getAsDouble();
                                        Log.e("April", "" + monthwise_donations[3]);
                                    }
                                    //May
                                    if (jsonObject.get("Month").getAsString().equals("May")) {
                                        monthwise_donations[4] = jsonObject.get("donations").getAsDouble();
                                        Log.e("May", "" + monthwise_donations[4]);
                                    }
                                    //June
                                    if (jsonObject.get("Month").getAsString().equals("June")) {
                                        monthwise_donations[5] = jsonObject.get("donations").getAsDouble();
                                        Log.e("June", "" + monthwise_donations[5]);
                                    }
                                    //July
                                    if (jsonObject.get("Month").getAsString().equals("July")) {
                                        monthwise_donations[6] = jsonObject.get("donations").getAsDouble();
                                        Log.e("July", "" + monthwise_donations[6]);
                                    }
                                    //August
                                    if (jsonObject.get("Month").getAsString().equals("August")) {
                                        monthwise_donations[7] = jsonObject.get("donations").getAsDouble();
                                        Log.e("August", "" + monthwise_donations[7]);
                                    }
                                    //September
                                    if (jsonObject.get("Month").getAsString().equals("September")) {
                                        monthwise_donations[8] = jsonObject.get("donations").getAsDouble();
                                        Log.e("September", "" + monthwise_donations[8]);
                                    }
                                    //October
                                    if (jsonObject.get("Month").getAsString().equals("October")) {
                                        monthwise_donations[9] = jsonObject.get("donations").getAsDouble();
                                        Log.e("October", "" + monthwise_donations[9]);
                                    }
                                    //November
                                    if (jsonObject.get("Month").getAsString().equals("November")) {
                                        monthwise_donations[10] = jsonObject.get("donations").getAsDouble();
                                        Log.e("November", "" + monthwise_donations[10]);
                                    }
                                    //December
                                    if (jsonObject.get("Month").getAsString().equals("December")) {
                                        monthwise_donations[11] = jsonObject.get("donations").getAsDouble();
                                        Log.e("December", "" + monthwise_donations[10]);
                                    }
                                }

                            } else if (jsonArray.isJsonNull()) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            } else if (response==null) {
                                Toast.makeText(AdminHome.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminHome.this, "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void monthwiseDonationsDashboardThread() {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String monthwise_donations = "file:///android_asset/monthwise_donations.html";
                        monthwiseDonationDetailsWebView.loadUrl(monthwise_donations);
                        monthwiseDonationDetailsWebView.addJavascriptInterface(new WebAppInterface(), "Android");
                        monthwiseDonationDetailsWebView.getSettings().setJavaScriptEnabled(true);
                        getMonthwiseDonationsTotal();
                    }
                });
            }
        }).start();
    }

    public void sponsors(View view) {
        startActivity(new Intent(AdminHome.this, SponsorsTable.class));
    }

    public void admins(View view) {
        startActivity(new Intent(AdminHome.this, AdminsTable.class));
    }

    public void childs(View view) {
        startActivity(new Intent(AdminHome.this, ChildsTable.class));
    }

    public void orphanages(View view) {
        startActivity(new Intent(AdminHome.this, OrphanagesTable.class));
    }

    public void locations(View view) {
        startActivity(new Intent(AdminHome.this, LocationsTable.class));
    }

    public void adoptRequests(View view) {
        startActivity(new Intent(AdminHome.this, AdoptRequestsTable.class));
    }

    public void adoptStatus(View view) {
        startActivity(new Intent(AdminHome.this, AdoptiveStatusTable.class));
    }

    public void roles(View view) {
        startActivity(new Intent(AdminHome.this, RolesTable.class));
    }

    public void donations(View view) {
        startActivity(new Intent(AdminHome.this, DonationsTable.class));
    }

    public void orphanageActivities(View view) {
        startActivity(new Intent(AdminHome.this, OrphanageActivitiesTable.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendSponsorNotifications(View view) {
        //Create a Notification Channel
        NotificationChannel channel = new NotificationChannel("myChannelId", "myChannelName", NotificationManager.IMPORTANCE_DEFAULT);

        //Create a notification Manager object to set this created channel
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Create a Notification builder object
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannelId");
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("My Notification");
        builder.setContentText("This is a new notification generated from my App");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // Show the Notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendSponsorNotificationsEvent(View view) {
        NotificationChannel channel = new NotificationChannel("tapChannelID", "tapChannelName", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(this, SponsorHome.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "tapChannelID");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Tap Notification");
        builder.setContentText("This is a Tap notification generated from my App");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(2, builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sponsor_menu, menu);
        menu.removeItem(R.id.login);
        menu.removeItem(R.id.Home);
        menu.removeItem(R.id.donate);
        menu.removeItem(R.id.adopt);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon(R.drawable.back);
            item.setVisible(true);
            finish();
        } else if (item.getTitle().equals("Home")) {
            setIntent(new Intent(AdminHome.this, MainActivity.class));
        } else if (item.getTitle().equals("Logout")) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("store", MODE_PRIVATE);
            preferences.edit().clear().commit();
            startActivity(new Intent(this, MainActivity.class));
        } else if (item.getTitle().equals("Add")) {
            startActivity(new Intent(this, AddOrphanageActivities.class));
        } else {
            finish();
            finish();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class WebAppInterface {

        @JavascriptInterface
        public int getSuccess() {
            return success;
        }

        @JavascriptInterface
        public int getFailed() {
            return failed;
        }

        @JavascriptInterface
        public int getNewReq() {
            return new_req;
        }

        @JavascriptInterface
        public int getApproved() {
            return approved;
        }

        @JavascriptInterface
        public int getRejected() {
            return rejected;
        }

        @JavascriptInterface
        public double getJanDonations() {
            return monthwise_donations[0];
        }

        @JavascriptInterface
        public double getFebDonations() {
            return monthwise_donations[1];
        }

        @JavascriptInterface
        public double getMarDonations() {
            return monthwise_donations[2];
        }

        @JavascriptInterface
        public double getAprDonations() {
            return monthwise_donations[3];
        }

        @JavascriptInterface
        public double getMayDonations() {
            return monthwise_donations[4];
        }

        @JavascriptInterface
        public double getJunDonations() {
            return monthwise_donations[5];
        }

        @JavascriptInterface
        public double getJulDonations() {
            return monthwise_donations[6];
        }

        @JavascriptInterface
        public double getAugDonations() {
            return monthwise_donations[7];
        }

        @JavascriptInterface
        public double getSepDonations() {
            return monthwise_donations[8];
        }

        @JavascriptInterface
        public double getOctDonations() {
            return monthwise_donations[9];
        }

        @JavascriptInterface
        public double getNovDonations() {
            return monthwise_donations[10];
        }

        @JavascriptInterface
        public double getDecDonations() {
            return monthwise_donations[11];
        }
    }

}