package com.kaligotla.oms.AdminView.AdoptRequest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.CustomDateFormate;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdoptRequestsTableDetails extends AppCompatActivity {

    int req_no;
    TextInputLayout sponsor, admin, child, reason, date_of_req, last_checked, req_comment, next_check, adopted;
    Spinner reqStageSpinner;
    MaterialToolbar sponsor_toolbar;
    String[] reqStageSpinnerArray = {"New Request", "Approved", "Rejected"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_adopt_requests_table_details );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        sponsor = findViewById( R.id.sponsor);
        admin = findViewById( R.id.admin);
        child = findViewById( R.id.child);
        reason = findViewById( R.id.reason);
        reqStageSpinner = findViewById( R.id.reqStageSpinner);
        date_of_req = findViewById( R.id.date_of_req);
        last_checked = findViewById( R.id.last_checked);
        req_comment = findViewById( R.id.req_comment);
        next_check = findViewById( R.id.next_check);
        adopted = findViewById( R.id.adopted);

        req_no = getIntent().getIntExtra("req_no",0);
        getAdoptRequest(req_no);

        date_of_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdoptRequestsTableDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date_of_req.getEditText().setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        last_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdoptRequestsTableDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                last_checked.getEditText().setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        next_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdoptRequestsTableDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                next_check.getEditText().setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        adopted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdoptRequestsTableDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                adopted.getEditText().setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    private void getAdoptRequest(int req_no) {
        Log.e("inside getAdoptRequest",""+req_no);
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .getAdoptRequestByID(req_no)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray array = response.body().get("data").getAsJsonArray();
                        if(array.size()>0){
                            JsonObject jsonObject = array.get(0).getAsJsonObject();

                            if (!jsonObject.get("user_id").isJsonNull()) {
                                sponsor.getEditText().setText(jsonObject.get("user_id").getAsString());
                            } else sponsor.getEditText().setText(null);

                            if (!jsonObject.get("admin_id").isJsonNull()) {
                                admin.getEditText().setText(jsonObject.get("admin_id").getAsString());
                            } else admin.getEditText().setText(null);

                            if (!jsonObject.get("child_id").isJsonNull()) {
                                child.getEditText().setText(jsonObject.get("child_id").getAsString());
                            } else child.getEditText().setText(null);

                            if (!jsonObject.get("reason").isJsonNull()) {
                                reason.getEditText().setText(jsonObject.get("reason").getAsString());
                            } else reason.getEditText().setText(null);

                            if (!jsonObject.get("req_stage").isJsonNull()) {
                                String existingGender = jsonObject.get("req_stage").getAsString();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(AdoptRequestsTableDetails.this, android.R.layout.simple_list_item_1, reqStageSpinnerArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                reqStageSpinner.setAdapter(arrayAdapter);
                                if (existingGender != null) {
                                    int spinnerPosition = arrayAdapter.getPosition(existingGender);
                                    reqStageSpinner.setSelection(spinnerPosition);
                                } else reqStageSpinner.setSelection(1);
                            } else reqStageSpinner.setSelection(1);

                            if (!jsonObject.get("date_of_req").isJsonNull()) {
                                date_of_req.getEditText().setText(CustomDateFormate.convert(jsonObject.get("date_of_req").getAsString()));
                            } else date_of_req.getEditText().setText(null);

                            if (!jsonObject.get("last_checked").isJsonNull()) {
                                last_checked.getEditText().setText(CustomDateFormate.convert(jsonObject.get("last_checked").getAsString()));
                            } else last_checked.getEditText().setText(null);

                            if (!jsonObject.get("req_comment").isJsonNull()) {
                                req_comment.getEditText().setText(jsonObject.get("req_comment").getAsString());
                            } else req_comment.getEditText().setText(null);

                            if (!jsonObject.get("next_check").isJsonNull()) {
                                next_check.getEditText().setText(CustomDateFormate.convert(jsonObject.get("next_check").getAsString()));
                            } else next_check.getEditText().setText(null);

                            if (!jsonObject.get("adopted").isJsonNull()) {
                                adopted.getEditText().setText(CustomDateFormate.convert(jsonObject.get("adopted").getAsString()));
                            } else adopted.getEditText().setText(null);
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
    }

    public void cancel(View view) {
        finish();
    }

    public void update(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sponsor_menu, menu );
        menu.removeItem( R.id.login );
        menu.removeItem( R.id.Home );
        menu.removeItem( R.id.donate );
        menu.removeItem( R.id.adopt );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            item.setVisible( true );
            finish();
        } else if (item.getTitle().equals( "Home" )) {
            setIntent( new Intent( AdoptRequestsTableDetails.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( AdoptRequestsTableDetails.this, MainActivity.class ) );
        } else if (item.getTitle().equals("Add")) {
            startActivity( new Intent( this, AddOrphanageActivities.class ) );
        } else {
            finish();
            finish();
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

}