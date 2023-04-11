package com.kaligotla.oms.AdminView.AdoptRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.Child.Child;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.CustomDateFormate;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewAdoptRequest extends AppCompatActivity {

    int aid;
    TextInputLayout sponsor, admin, child, reason, date_of_req, last_checked, req_comment, next_check, adopted;
    TextInputEditText dateOfReqEditText, lastCheckedEditText, nextCheckEditText, adoptedEditText;
    Spinner reqStageSpinner;
    MaterialToolbar sponsor_toolbar;
    String[] reqStageSpinnerArray = {"New Request", "Approved", "Rejected"};
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_adopt_request );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        sponsor = findViewById( R.id.sponsor);
        admin = findViewById( R.id.admin);
        child = findViewById( R.id.child);
        reason = findViewById( R.id.reason);
        reqStageSpinner = findViewById( R.id.reqStageSpinner);
        date_of_req = findViewById( R.id.date_of_req);
        dateOfReqEditText = findViewById(R.id.dateOfReqEditText);
        last_checked = findViewById( R.id.last_checked);
        lastCheckedEditText = findViewById(R.id.lastCheckedEditText);
        req_comment = findViewById( R.id.req_comment);
        next_check = findViewById( R.id.next_check);
        nextCheckEditText = findViewById(R.id.nextCheckEditText);
        adopted = findViewById( R.id.adopted);
        adoptedEditText = findViewById(R.id.adoptedEditText);
        aid = getIntent().getIntExtra("aid",0);

        dateOfReqEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewAdoptRequest.this,
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

        lastCheckedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewAdoptRequest.this,
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

        nextCheckEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewAdoptRequest.this,
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

        adoptedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewAdoptRequest.this,
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
        arrayAdapter = new ArrayAdapter(NewAdoptRequest.this, android.R.layout.simple_list_item_1, reqStageSpinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reqStageSpinner.setAdapter(arrayAdapter);
    }

    public void cancel(View view) {
        finish();
    }

    public void create(View view) {
        AdoptRequest newAdoptReq = new AdoptRequest();

        newAdoptReq.setSponsor(new Sponsor(Integer.parseInt(String.valueOf(sponsor.getEditText().getText()))));
        newAdoptReq.setAdmin(new Admin(Integer.parseInt(String.valueOf(admin.getEditText().getText()))));
        newAdoptReq.setChild(new Child(Integer.parseInt(String.valueOf(child.getEditText().getText()))));
        newAdoptReq.setReason(String.valueOf(reason.getEditText().getText()));
        newAdoptReq.setReq_stage((String) reqStageSpinner.getSelectedItem());
        newAdoptReq.setDate_of_req(CustomDateFormate.convert(String.valueOf(date_of_req.getEditText().getText())));
        newAdoptReq.setLast_checked(CustomDateFormate.convert(String.valueOf(last_checked.getEditText().getText())));
        newAdoptReq.setReq_comment(String.valueOf(req_comment.getEditText().getText()));
        newAdoptReq.setNext_check(CustomDateFormate.convert(String.valueOf(next_check.getEditText().getText())));
        newAdoptReq.setAdopted(CustomDateFormate.convert(String.valueOf(adopted.getEditText().getText())));


        Log.e("update in adopt req details",newAdoptReq.toString());
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .newAdoptReq(newAdoptReq)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject data = response.body().getAsJsonObject( "data" );
                        if (data.get("affectedRows").getAsInt()==1) {
                            Toast.makeText(NewAdoptRequest.this,"Created new adopt request ID "+data.get("insertId")+" in Database",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(NewAdoptRequest.this,"Unable to update, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(NewAdoptRequest.this,"DB connection failed, please try after sometime",Toast.LENGTH_SHORT).show();
                    }
                });
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
            setIntent( new Intent( NewAdoptRequest.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( NewAdoptRequest.this, MainActivity.class ) );
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