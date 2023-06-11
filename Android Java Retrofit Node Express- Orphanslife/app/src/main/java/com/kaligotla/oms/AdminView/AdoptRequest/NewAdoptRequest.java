package com.kaligotla.oms.AdminView.AdoptRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.AdoptiveStatus.AdoptiveStatus;
import com.kaligotla.oms.AdminView.Child.Child;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Sponsor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewAdoptRequest extends AppCompatActivity {
    int aid;
    MaterialTextView selectedSponsorTextView, selectedAdminTextView, selectedChildTextView;
    TextInputLayout reason, date_of_req, last_checked, req_comment, next_check, adopted;
    TextInputEditText dateOfReqEditText, lastCheckedEditText, nextCheckEditText, adoptedEditText;
    Spinner reqStageSpinner, sponsorSpinner, adminSpinner, childSpinner;
    MaterialToolbar sponsor_toolbar;
    String[] reqStageSpinnerArray = {"New Request", "Approved", "Rejected"};
    ArrayAdapter arrayAdapter;
    Dialog sponsorDialog, adminDialog, childDialog;
    List<Sponsor> sponsorsList;
    List<Admin> adminsList;
    List<Child> childsList;
    ShapeableImageView close;
    Sponsor selectedSponsor = new Sponsor();
    Admin selectedAdmin = new Admin();
    Child selectedChild = new Child();

    public class Array implements Serializable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return id+" - "+name;
        }
    }
    private Array[] sponsorArray;
    private Array[] adminArray;
    private Array[] childArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_adopt_request );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        selectedSponsorTextView = findViewById( R.id.selectedSponsorTextView);
        selectedAdminTextView = findViewById( R.id.selectedAdminTextView);
        selectedChildTextView = findViewById( R.id.selectedChildTextView);
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
        sponsorDialog = new Dialog(this);
        adminDialog = new Dialog(this);
        childDialog = new Dialog(this);
        aid = getIntent().getIntExtra("aid",0);
        getSponsors();
        getAdmins();
        getChilds();

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
        sponsorSpinner();
        adminSpinner();
        childSpinner();

        arrayAdapter = new ArrayAdapter(NewAdoptRequest.this, android.R.layout.simple_list_item_1, reqStageSpinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reqStageSpinner.setAdapter(arrayAdapter);
    }

    public void sponsorSpinner() {
        selectedSponsorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sponsorArray = new Array[sponsorsList.size()+1];
                updateSponsorSpinner();
                sponsorDialog.setContentView(R.layout.sponsor_spinner);
                sponsorSpinner = (Spinner) sponsorDialog.findViewById(R.id.sponsorSpinner);
                close = sponsorDialog.findViewById(R.id.close);
                ArrayAdapter arrayAdapter = null;
                arrayAdapter = new ArrayAdapter(NewAdoptRequest.this, android.R.layout.simple_list_item_1, (Array[]) sponsorArray);
                sponsorSpinner.setAdapter(arrayAdapter);
                sponsorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedSponsorTextView.setText(sponsorArray[position].toString());
                        for(Sponsor s: sponsorsList) {
                            if(s.getSponsor_id()==sponsorArray[position].getId()){
                                selectedSponsor = new Sponsor(s);
                                Log.e("selectedSponsor",selectedSponsor.toString());
                            }
                            if(position>0) {
                                sponsorDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        sponsorDialog.dismiss();
                    }
                });
                sponsorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sponsorDialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sponsorDialog.dismiss();
                    }
                });
            }
        });
    }

    public void updateSponsorSpinner() {
        Array s = new Array();
        s.setId(0);
        s.setName("Click to get Sponsor List");
        sponsorArray[0] = s;
        for (int i = 0; i < sponsorsList.size(); i++) {
            s = new Array();
            s.setId(sponsorsList.get(i).getSponsor_id());;
            s.setName(sponsorsList.get(i).getSponsor_name());
            sponsorArray[i+1] = s;
        }
    }

    public void getSponsors() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.SPONSOR_URL )
                .build()
                .create( DBService.class )
                .sponsors( this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""))
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            sponsorsList = new ArrayList<>();
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                Sponsor sponsor = new Sponsor();
                                if(!jsonObject.get( "sponsor_id" ).isJsonNull()) {
                                    sponsor.setSponsor_id(jsonObject.get("sponsor_id").getAsInt());
                                }
                                if(!jsonObject.get( "sponsor_name" ).isJsonNull()) {
                                    sponsor.setSponsor_name(jsonObject.get("sponsor_name").getAsString());
                                }
                                sponsorsList.add( sponsor );
                            }
                        }
                        else if(jsonArray.size()==0) {
                            Toast.makeText( NewAdoptRequest.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( NewAdoptRequest.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void adminSpinner() {
        selectedAdminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminArray = new Array[adminsList.size()+1];
                updateAdminSpinner();
                adminDialog.setContentView(R.layout.admin_spinner);
                adminSpinner = (Spinner) adminDialog.findViewById(R.id.adminSpinner);
                close = adminDialog.findViewById(R.id.close);
                ArrayAdapter arrayAdapter = null;
                arrayAdapter = new ArrayAdapter(NewAdoptRequest.this, android.R.layout.simple_list_item_1, (Array[]) adminArray);
                adminSpinner.setAdapter(arrayAdapter);
                adminSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedAdminTextView.setText(adminArray[position].toString());
                        for(Admin a: adminsList) {
                            if(a.getAdmin_id()==adminArray[position].getId()){
                                selectedAdmin = new Admin(a);
                                Log.e("selectedAdmin",selectedAdmin.toString());
                            }
                            if(position>0) {
                                adminDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        adminDialog.dismiss();
                    }
                });
                adminDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                adminDialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adminDialog.dismiss();
                    }
                });
            }
        });
    }

    public void updateAdminSpinner() {
        Array a = new Array();
        a.setId(0);
        a.setName("Click to get Admin List");
        adminArray[0] = a;
        for (int i = 0; i < adminsList.size(); i++) {
            a = new Array();
            a.setId(adminsList.get(i).getAdmin_id());;
            a.setName(adminsList.get(i).getAdmin_name());
            adminArray[i+1] = a;
        }
    }

    public void getAdmins() {
        Log.e("API Token", this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""));
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.ADMIN_URL )
                .build()
                .create( DBService.class )
                .admins(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""))
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            adminsList = new ArrayList<>();
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                Admin admin = new Admin();
                                admin.setAdmin_id( Integer.parseInt( jsonObject.get( "admin_id" ).toString() ));
                                admin.setAdmin_name(jsonObject.get( "admin_name" ).toString());
                                adminsList.add( admin );
                            }
                        }
                        else if(jsonArray.size()==0) {
                            Toast.makeText( NewAdoptRequest.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( NewAdoptRequest.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void childSpinner() {
        selectedChildTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childArray = new Array[childsList.size()+1];
                updateChildSpinner();
                childDialog.setContentView(R.layout.child_spinner);
                childSpinner = (Spinner) childDialog.findViewById(R.id.childSpinner);
                close = childDialog.findViewById(R.id.close);
                ArrayAdapter arrayAdapter = null;
                arrayAdapter = new ArrayAdapter(NewAdoptRequest.this, android.R.layout.simple_list_item_1, (Array[]) childArray);
                childSpinner.setAdapter(arrayAdapter);
                childSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedChildTextView.setText(childArray[position].toString());
                        for(Child c: childsList) {
                            if(c.getChild_id()==childArray[position].getId()){
                                selectedChild = new Child(c);
                                Log.e("selectedAdmin",selectedChild.toString());
                            }
                            if(position>0) {
                                childDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        childDialog.dismiss();
                    }
                });
                childDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                childDialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        childDialog.dismiss();
                    }
                });
            }
        });
    }

    public void updateChildSpinner() {
        Array c = new Array();
        c.setId(0);
        c.setName("Click to get Child List");
        childArray[0] = c;
        for (int i = 0; i < childsList.size(); i++) {
            c = new Array();
            c.setId(childsList.get(i).getChild_id());;
            c.setName(childsList.get(i).getChild_name());
            childArray[i+1] = c;
        }
    }

    public void getChilds() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.ORPHANAGE_URL )
                .build()
                .create( DBService.class )
                .childs( this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            childsList = new ArrayList<>();
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                Child child = new Child();
                                AdoptiveStatus adoptiveStatus = new AdoptiveStatus();
                                if(!jsonObject.get( "child_id" ).isJsonNull()) {
                                    child.setChild_id( jsonObject.get( "child_id" ).getAsInt() );
                                } else child.setChild_id(0);
                                child.setChild_name(jsonObject.get( "child_name" ).getAsString());
                                childsList.add( child );
                            }
                        }
                        else if(jsonArray.size()==0) {
                            Toast.makeText( NewAdoptRequest.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( NewAdoptRequest.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void cancel(View view) {
        finish();
    }

    public void create(View view) {
        AdoptRequest newAdoptReq = new AdoptRequest();

        newAdoptReq.setSponsor(selectedSponsor);
        newAdoptReq.setAdmin(selectedAdmin);
        newAdoptReq.setChild(selectedChild);
        newAdoptReq.setReason(String.valueOf(reason.getEditText().getText()));
        newAdoptReq.setReq_stage((String) reqStageSpinner.getSelectedItem());
        newAdoptReq.setDate_of_req(date_of_req.getEditText().getText().toString());
        newAdoptReq.setLast_checked(last_checked.getEditText().getText().toString());
        newAdoptReq.setReq_comment(req_comment.getEditText().getText().toString());
        newAdoptReq.setNext_check(next_check.getEditText().getText().toString());
        newAdoptReq.setAdopted(adopted.getEditText().getText().toString());

        Log.e("update in adopt req details",newAdoptReq.toString());
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BUSINESS_URL)
                .build()
                .create(DBService.class)
                .newAdoptReq(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""), newAdoptReq)
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