package com.kaligotla.oms.AdminView.Sponsor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewSponsor extends AppCompatActivity {

    TextInputLayout name, dob, govt_id, mobile, email, password, user_address, location, spouce_name, spouce_dob, spouce_govt_id, spouce_mobile, donation_id;
    TextInputEditText dobEditText;
    Spinner genderSpinner, govtIdTypeSpinner, maritalStatusSpinner, spouceGovtIdTypeSpinner;
    ShapeableImageView user_image, spouce_image;
    MaterialToolbar sponsor_toolbar;
    String[] genderSpinnerArray = {"Male", "Female", "Other"};
    String[] govtIdTypeSpinnerArray = {"Aadhaar", "Voter", "Driving License", "PAN", "Passport"};
    String[] maritalStatusSpinnerArray = {"Single", "Married", "Divorced", "Break_UP", "Widowed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sponsor);
        name = findViewById( R.id.name);
        dob = findViewById( R.id.dob);
        dobEditText = findViewById(R.id.dobEditText);
        genderSpinner = findViewById( R.id.genderSpinner);
        govtIdTypeSpinner = findViewById( R.id.govtIdTypeSpinner );
        govt_id = findViewById( R.id.govt_id );
        mobile = findViewById( R.id.mobile );
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        maritalStatusSpinner = findViewById( R.id.maritalStatusSpinner );
        user_image = findViewById( R.id.user_image );
        user_address = findViewById( R.id.user_address );
        location = findViewById( R.id.location );
        spouce_name = findViewById( R.id.spouce_name );
        spouce_dob = findViewById( R.id.spouce_dob );
        spouceGovtIdTypeSpinner = findViewById( R.id.spouceGovtIdTypeSpinner );
        spouce_govt_id = findViewById( R.id.spouce_govt_id );
        spouce_mobile = findViewById( R.id.spouce_mobile );
        spouce_image = findViewById( R.id.spouce_image );
        donation_id = findViewById( R.id.donation_id );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewSponsor.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dob.getEditText().setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayAdapter genderAdapter = new ArrayAdapter(NewSponsor.this, android.R.layout.simple_list_item_1, genderSpinnerArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        ArrayAdapter sponsorGovtIdTypeAdapter = new ArrayAdapter(NewSponsor.this, android.R.layout.simple_list_item_1, govtIdTypeSpinnerArray);
        sponsorGovtIdTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        govtIdTypeSpinner.setAdapter(sponsorGovtIdTypeAdapter);

        ArrayAdapter maritalStatusAdapter = new ArrayAdapter(NewSponsor.this, android.R.layout.simple_list_item_1, maritalStatusSpinnerArray);
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalStatusSpinner.setAdapter(maritalStatusAdapter);

        ArrayAdapter spouceGovtIdTypeAdapter = new ArrayAdapter(NewSponsor.this, android.R.layout.simple_list_item_1, govtIdTypeSpinnerArray);
        spouceGovtIdTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spouceGovtIdTypeSpinner.setAdapter(spouceGovtIdTypeAdapter);
    }

    public void create(View view) {
        Sponsor newSponsor = new Sponsor();
        newSponsor.setSponsor_id(getIntent().getIntExtra("sid",0));
        newSponsor.setSponsor_name(name.getEditText().getText().toString());
        newSponsor.setSponsor_dob(dob.getEditText().getText().toString());
        newSponsor.setSponsor_gender(genderSpinner.getSelectedItem().toString());
        newSponsor.setSponsor_govt_id_type(govtIdTypeSpinner.getSelectedItem().toString());
        newSponsor.setSponsor_govt_id(govt_id.getEditText().getText().toString());
        newSponsor.setSponsor_mobile(mobile.getEditText().getText().toString());
        newSponsor.setSponsor_email(email.getEditText().getText().toString());
        newSponsor.setSponsor_password(password.getEditText().getText().toString());
        newSponsor.setMarital_status(maritalStatusSpinner.getSelectedItem().toString());
        newSponsor.setSponsor_address(user_address.getEditText().getText().toString());
        //user_image, location, spouce_image, Donation
        newSponsor.setSpouce_name(spouce_name.getEditText().getText().toString());
        newSponsor.setSpouce_dob(spouce_dob.getEditText().getText().toString());
        newSponsor.setSpouce_govt_id_type(spouceGovtIdTypeSpinner.getSelectedItem().toString());
        newSponsor.setSpouce_govt_id(spouce_govt_id.getEditText().getText().toString());
        newSponsor.setSpouce_mobile(spouce_mobile.getEditText().getText().toString());

        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .register(newSponsor)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject data = response.body().getAsJsonObject( "data" );
                        if (data.get("affectedRows").getAsInt()==1) {
                            Log.e("updateSponsor",newSponsor.toString());
                            Toast.makeText(NewSponsor.this,"Created Sponsor ID "+data.get("insertId")+" in Database",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(NewSponsor.this,"Unable to create sponsor, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("updateSponsor",newSponsor.toString());
                        Toast.makeText(NewSponsor.this,"DB Connection Failed, please try after sometime",Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}