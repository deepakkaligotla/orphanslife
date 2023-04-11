package com.kaligotla.oms.AdminView.Admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Role.Role;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.CustomDateFormate;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.Essentials.ImageLoadTask;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;
import com.kaligotla.oms.orphanage.Orphanage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminTableDetails extends AppCompatActivity {
    int aid;
    Admin admin;
    TextInputLayout name, dob, govt_id, mobile, email, password, address, location;
    TextInputEditText dobEditText;
    MaterialTextView selectedOrphanage;
    Spinner genderSpinner, govtIdTypeSpinner, roleSpinner, addOrphanageSpinner;
    ShapeableImageView image, close;
    MaterialToolbar sponsor_toolbar;
    String[] genderSpinnerArray = {"Male", "Female", "Other"};
    String[] govtIdTypeSpinnerArray = {"Aadhaar", "Voter", "Driving License", "PAN", "Passport"};
    String[] roleSpinnerArray = {"Volunteer", "Guardian", "Super_Admin"};
    String[] orphanageNameArray;
    List<Orphanage> orphanageList;
    Orphanage orphanage;
    Dialog myDialog;
    Orphanage selectedOrphanageID = new Orphanage();
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_table_details);
        sponsor_toolbar = findViewById(R.id.sponsor_toolbar);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        dobEditText = findViewById(R.id.dobEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        govtIdTypeSpinner = findViewById(R.id.govtIdTypeSpinner);
        govt_id = findViewById(R.id.govt_id);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        location = findViewById(R.id.location);
        roleSpinner = findViewById(R.id.roleSpinner);
        selectedOrphanage = findViewById(R.id.selectedOrphanage);
        image = findViewById(R.id.image);
        aid = getIntent().getIntExtra("aid", 0);
        orphanageList = new ArrayList<>();
        myDialog = new Dialog(this);

        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminTableDetails.this,
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
        getOrphanages();
        getAdminThread(aid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar(sponsor_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDialogThread();
        selectedOrphanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrphangeSpinnerThread();
            }
        });
    }
    private void getOrphanages() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .orphanages()
                .enqueue(new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray("data");
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                orphanage = new Orphanage();
                                orphanage.setId(Integer.parseInt(jsonObject.get("id").toString()));
                                if (!jsonObject.get("address").isJsonNull()) {
                                    orphanage.setAddress(jsonObject.get("address").getAsString());
                                } else orphanage.setAddress(null);
                                orphanage.setIn_home(jsonObject.get("in_home").getAsInt());
                                orphanage.setAdoptable(jsonObject.get("adoptable").getAsInt());
                                orphanageList.add(orphanage);
                            }
                        } else if (jsonArray.size() == 0) {
                            Toast.makeText( AdminTableDetails.this, "Unable to get orphanage details from DB", Toast.LENGTH_SHORT ).show();
                        }
                        orphanageNameArray = new String[orphanageList.size()];
                        updateOrphangeSpinnerThread();
                        Log.e("orphanageList from DB",""+orphanageList.size());
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText( DBHelper.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                });

    }

    public void updateOrphangeSpinnerThread() {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        orphanageNameArray[0] = "Select Orphanage";
                        for (int i = 1; i < orphanageList.size(); i++) {
                            orphanageNameArray[i] = orphanageList.get(i - 1).getAddress();
                        }
                        myDialog.setContentView(R.layout.orphange_spinner);
                        addOrphanageSpinner = myDialog.findViewById(R.id.addOrphanageSpinner);
                        close = myDialog.findViewById(R.id.close);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(AdminTableDetails.this, android.R.layout.simple_list_item_1, orphanageNameArray);
                        addOrphanageSpinner.setAdapter(arrayAdapter);
                        addOrphanageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selectedOrphanage.setText(orphanageNameArray[position]);
                                for (Orphanage o : orphanageList) {
                                    if (o.getAddress().equals(orphanageNameArray[position])) {
                                        selectedOrphanageID.setId(o.getId());
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                myDialog.dismiss();
                            }
                        });
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog.show();
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myDialog.dismiss();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void getAdminThread(int aid) {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .baseUrl(Constants.BASE_URL)
                                .build()
                                .create(DBService.class)
                                .getAdminByID(aid)
                                .enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        admin = new Admin();
                                        JsonArray array = response.body().get("data").getAsJsonArray();
                                        if (array.size() > 0) {
                                            JsonObject jsonObject = array.get(0).getAsJsonObject();

                                            name.getEditText().setText(jsonObject.get("admin_name").getAsString());

                                            if (!jsonObject.get("admin_dob").isJsonNull())
                                                dob.getEditText().setText(CustomDateFormate.convert(jsonObject.get("admin_dob").toString()));
                                            else dob.getEditText().setText(null);

                                            if (!jsonObject.get("admin_gender").isJsonNull()) {
                                                String existingGender = jsonObject.get("admin_gender").getAsString();
                                                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminTableDetails.this, android.R.layout.simple_list_item_1, genderSpinnerArray);
                                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                genderSpinner.setAdapter(arrayAdapter);
                                                if (existingGender != null) {
                                                    int spinnerPosition = arrayAdapter.getPosition(existingGender);
                                                    genderSpinner.setSelection(spinnerPosition);
                                                } else genderSpinner.setSelection(1);
                                            } else genderSpinner.setSelection(1);

                                            if (!jsonObject.get("admin_govt_id_type").isJsonNull()) {
                                                String existingGovtIdType = jsonObject.get("admin_govt_id_type").getAsString();
                                                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminTableDetails.this, android.R.layout.simple_list_item_1, govtIdTypeSpinnerArray);
                                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                govtIdTypeSpinner.setAdapter(arrayAdapter);
                                                if (existingGovtIdType != null) {
                                                    int spinnerPosition = arrayAdapter.getPosition(existingGovtIdType);
                                                    govtIdTypeSpinner.setSelection(spinnerPosition);
                                                } else govtIdTypeSpinner.setSelection(1);
                                            } else govtIdTypeSpinner.setSelection(1);

                                            if (!jsonObject.get("admin_govt_id").isJsonNull())
                                                govt_id.getEditText().setText(jsonObject.get("admin_govt_id").getAsString());
                                            else govt_id.getEditText().setText(null);

                                            if (!jsonObject.get("admin_mobile").isJsonNull())
                                                mobile.getEditText().setText(jsonObject.get("admin_mobile").getAsString());
                                            else mobile.getEditText().setText(null);

                                            email.getEditText().setText(jsonObject.get("admin_email").getAsString());

                                            password.getEditText().setText(jsonObject.get("admin_password").getAsString());

                                            if (!jsonObject.get("admin_address").isJsonNull())
                                                address.getEditText().setText(jsonObject.get("admin_address").getAsString());
                                            else address.getEditText().setText(null);

                                            if (!jsonObject.get("admin_location_id").isJsonNull())
                                                location.getEditText().setText(jsonObject.get("admin_location_id").getAsString());
                                            else location.getEditText().setText(null);

                                            if (!jsonObject.get("role_id").isJsonNull()) {
                                                admin.setRole(new Role(jsonObject.get("role_id").getAsInt()));
                                                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminTableDetails.this, android.R.layout.simple_list_item_1, roleSpinnerArray);
                                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                roleSpinner.setAdapter(arrayAdapter);
                                                roleSpinner.setSelection(admin.getRole().getId() - 1);
                                            } else roleSpinner.setSelection(1);

                                            if (!jsonObject.get("admin_orphanage_id").isJsonNull()) {
                                                int adminOrphanageId = jsonObject.get("admin_orphanage_id").getAsInt();
                                                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminTableDetails.this, android.R.layout.simple_list_item_1, orphanageNameArray);
                                                addOrphanageSpinner.setAdapter(arrayAdapter);
                                                    addOrphanageSpinner.setSelection(adminOrphanageId);
                                                } else if(jsonObject.get("admin_orphanage_id").isJsonNull()) {
                                                addOrphanageSpinner.setSelection(1);
                                            }
                                            myDialog.dismiss();

                                            if(!jsonObject.get( "admin_image" ).isJsonNull())
                                                new ImageLoadTask(jsonObject.get( "admin_image" ).getAsString(), image).execute();
                                            else image.setBackgroundResource( R.drawable.image_not_available );

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                    }

                                });
                    }
                });
            }
        }).start();
    }

    private void myDialogThread() {
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                });
            }
        }).start();
    }

    public void cancel(View view) {
        finish();
    }

    public void update(View view) {
        Admin updatedAdmin = new Admin();

        updatedAdmin.setAdmin_id(getIntent().getIntExtra("aid",0));
        updatedAdmin.setAdmin_name(name.getEditText().getText().toString());
        updatedAdmin.setAdmin_dob(dob.getEditText().getText().toString());
        updatedAdmin.setAdmin_gender(genderSpinner.getSelectedItem().toString());
        updatedAdmin.setAdmin_govt_id_type(govtIdTypeSpinner.getSelectedItem().toString());
        updatedAdmin.setAdmin_govt_id(govt_id.getEditText().getText().toString());
        updatedAdmin.setAdmin_mobile(mobile.getEditText().getText().toString());
        updatedAdmin.setAdmin_email(email.getEditText().getText().toString());
        updatedAdmin.setAdmin_password(password.getEditText().getText().toString());
        updatedAdmin.setAddress(address.getEditText().getText().toString());
        //location
        //role
        updatedAdmin.setOrphanage(admin.getOrphanage());
        //Image
        Log.e("update in admin details",updatedAdmin.toString());
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .updateAdmin(aid, updatedAdmin)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject data = response.body().getAsJsonObject( "data" );
                        if (data.get("affectedRows").getAsInt()==1) {
                            Toast.makeText(AdminTableDetails.this,"Updated Admin ID "+aid+" in Database",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(AdminTableDetails.this,"Unable to update, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AdminTableDetails.this,"DB connection failed, please try after sometime",Toast.LENGTH_SHORT).show();
                    }
                });
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
            setIntent(new Intent(AdminTableDetails.this, MainActivity.class));
        } else if (item.getTitle().equals("Logout")) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("store", MODE_PRIVATE);
            preferences.edit().putBoolean("guardian_logged_in", false).commit();
            preferences.edit().putBoolean("sponsor_logged_in", false).commit();
            preferences.edit().putBoolean("volunteer_logged_in", false).commit();
            preferences.edit().putBoolean("super_admin_logged_in", false).commit();
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

}