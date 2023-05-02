package com.kaligotla.oms.OrphanageActivities;

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
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.AdminHome;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.R;
import com.kaligotla.oms.orphanage.Orphanage;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOrphanageActivities extends AppCompatActivity {
    Spinner addOrphanageSpinner;
    ShapeableImageView close;
    List<Orphanage> orphanageList;
    String[] orphanageNameArray;
    Toolbar sponsor_toolbar;
    Dialog myDialog;
    MaterialTextView selectedOrphanage;
    Orphanage selectedOrphanageID = new Orphanage();
    Orphanage orphanage;
    TextInputLayout addOrphanageEventName;
    OrphanageActivities orphanageActivities = new OrphanageActivities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_orphanage_activities );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        selectedOrphanage = findViewById(R.id.selectedOrphanage);
        addOrphanageEventName = findViewById(R.id.addOrphanageEventName);
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        orphanageList = new ArrayList<>();
        getOrphanages();
        myDialog = new Dialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedOrphanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orphanageNameArray = new String[orphanageList.size()];
                updateOrphangeSpinner();
                myDialog.setContentView(R.layout.orphange_spinner);
                addOrphanageSpinner =(Spinner) myDialog.findViewById(R.id.addOrphanageSpinner);
                close = myDialog.findViewById(R.id.close);
                ArrayAdapter arrayAdapter = new ArrayAdapter(AddOrphanageActivities.this, android.R.layout.simple_list_item_1,orphanageNameArray);
                addOrphanageSpinner.setAdapter(arrayAdapter);
                addOrphanageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedOrphanage.setText(orphanageNameArray[position]);
                        for(Orphanage o: orphanageList) {
                            if(o.getAddress().equals(orphanageNameArray[position])){
                                selectedOrphanageID.setId(o.getId());
                            }
                            if(position>0) {
                                myDialog.dismiss();
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

    public void getOrphanages() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .orphanages( this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""))
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                orphanage = new Orphanage();
                                orphanage.setId( Integer.parseInt( jsonObject.get( "id" ).toString() ));
                                if(!jsonObject.get( "address" ).isJsonNull()) {
                                    orphanage.setAddress( jsonObject.get( "address" ).getAsString() );}
                                else orphanage.setAddress(null);
                                orphanage.setIn_home(jsonObject.get( "in_home" ).getAsInt());
                                orphanage.setAdoptable(jsonObject.get( "adoptable" ).getAsInt());
                                orphanageList.add( orphanage );
                            }
                        }
                        else if(jsonArray.size()==0) {
//                            Toast.makeText( DBHelper.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText( DBHelper.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void updateOrphangeSpinner() {
        orphanageNameArray[0] = "Click here to get Orphanages List";
        for (int i = 1; i < orphanageList.size(); i++) {
            orphanageNameArray[i] = orphanageList.get(i-1).getAddress();
        }
        Log.e("orphanageNameArray",""+orphanageNameArray.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sponsor_menu, menu );
        menu.removeItem( R.id.login );
        menu.removeItem( R.id.add );
        menu.removeItem( R.id.donate );
        menu.removeItem( R.id.adopt );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            finish();
        } else if (item.getTitle().equals( "Home" )) {
            startActivity( new Intent( this, AdminHome.class ) );
            finish();
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( this, MainActivity.class ) );
        } else {
            finish();
            finish();
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

    public void uploadImages(View view) {

    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        orphanageActivities.setOrphanage(selectedOrphanageID);
        Log.e("inside Save", ""+selectedOrphanageID.getId());
        orphanageActivities.setDetails(addOrphanageEventName.getEditText().getText().toString());
        orphanageActivities.setImage_1("");
        orphanageActivities.setImage_2("");
        orphanageActivities.setImage_3("");
        orphanageActivities.setImage_4("");
        orphanageActivities.setImage_5("");
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .newOrphanageActivity(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""), orphanageActivities)
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject jsonObjectMain = response.body().getAsJsonObject();
                        JsonObject jsonObject;
                        if (jsonObjectMain.get("status").getAsString().equals("success")) {
                            jsonObject = jsonObjectMain.getAsJsonObject("data");
                            if(jsonObject.get("affectedRows").getAsInt()==1) {
                                Toast.makeText( AddOrphanageActivities.this, "New Event ID - "+jsonObject.get("insertId")+" Added", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        else {
                            Toast.makeText( AddOrphanageActivities.this, "Orphanage should be selected", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( AddOrphanageActivities.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }
}