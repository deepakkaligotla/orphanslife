package com.kaligotla.oms.AdminView.AdoptiveStatus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdoptiveStatusTable extends AppCompatActivity {

    RecyclerView AdoptiveStatusTableRecyclerView;
    List<AdoptiveStatus> adoptiveStatusList;
    AdoptiveStatusTableListAdapter adoptiveStatusTableListAdapter;
    MaterialToolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_adoptive_status_table );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        AdoptiveStatusTableRecyclerView = findViewById( R.id.AdoptiveStatusTableRecyclerView );
    }

    @Override
    protected void onResume() {
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
        AdoptiveStatusTableRecyclerView = findViewById(R.id.AdoptiveStatusTableRecyclerView);
        adoptiveStatusList = new ArrayList<>();
        getAdoptiveStatus();
        adoptiveStatusTableListAdapter = new AdoptiveStatusTableListAdapter(this, adoptiveStatusList);
        AdoptiveStatusTableRecyclerView.setAdapter(adoptiveStatusTableListAdapter);
        AdoptiveStatusTableRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adoptiveStatusTableListAdapter.notifyDataSetChanged();
    }

    public void getAdoptiveStatus() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.ORPHANAGE_URL )
                .build()
                .create( DBService.class )
                .adoptstatus( this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token", ""))
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                AdoptiveStatus adoptiveStatus = new AdoptiveStatus();
                                adoptiveStatus.setAdoptive_status_id( Integer.parseInt( jsonObject.get( "adoptive_status_id" ).toString() ));
                                adoptiveStatus.setStatus(jsonObject.get( "status" ).getAsString());
                                adoptiveStatusList.add( adoptiveStatus );
                            }
                            adoptiveStatusTableListAdapter.notifyDataSetChanged();
                        }
                        else if(jsonArray.size()==0) {
//                            Toast.makeText( DBHelper.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                        Log.e( "adoptiveStatusList",""+adoptiveStatusList );
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText( DBHelper.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void cancel(View view) {
        finish();
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
            setIntent( new Intent( AdoptiveStatusTable.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( this, MainActivity.class ) );
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