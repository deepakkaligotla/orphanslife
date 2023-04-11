package com.kaligotla.oms.AdminView.Sponsor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SponsorsTable extends AppCompatActivity {

    RecyclerView SponsorsTableRecyclerView;
    List<Sponsor> sponsorsList;
    SponsorsTableListAdapter sponsorsTableListAdapter;
    Toolbar sponsor_toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_sponsors_table );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
    }

    @Override
    protected void onResume() {
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
        SponsorsTableRecyclerView = findViewById(R.id.SponsorsTableRecyclerView);
        sponsorsList = new ArrayList<>();
        getSponsors();
        sponsorsTableListAdapter = new SponsorsTableListAdapter(this, sponsorsList);
        SponsorsTableRecyclerView.setAdapter(sponsorsTableListAdapter);
        SponsorsTableRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        sponsorsTableListAdapter.notifyDataSetChanged();

    }

    public void getSponsors() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .sponsors( )
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                Sponsor sponsor = new Sponsor();
                                if(!jsonObject.get( "sponsor_id" ).isJsonNull()) {
                                    sponsor.setSponsor_id(jsonObject.get("sponsor_id").getAsInt());
                                }
                                if(!jsonObject.get( "sponsor_name" ).isJsonNull()) {
                                    sponsor.setSponsor_name(jsonObject.get("sponsor_name").getAsString());
                                }
                                if(!jsonObject.get( "sponsor_mobile" ).isJsonNull()) {
                                    sponsor.setSponsor_mobile( jsonObject.get( "sponsor_mobile" ).getAsString() );}
                                else sponsor.setSponsor_mobile(null);
                                sponsor.setSponsor_email(jsonObject.get( "sponsor_email" ).getAsString());
                                sponsorsList.add( sponsor );
                            }
                            sponsorsTableListAdapter.notifyDataSetChanged();
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

    public void cancel(View view) {
        finish();
    }

    public void newAdmin(View view) {
        startActivity(new Intent(this, NewSponsor.class));
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
            setIntent( new Intent( SponsorsTable.this, MainActivity.class ) );
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
