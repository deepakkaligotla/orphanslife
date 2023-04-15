package com.kaligotla.oms.AdminView.Admin;

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

public class AdminsTable extends AppCompatActivity {

    RecyclerView AdminsTableRecyclerView;
    List<Admin> adminList;
    AdminsTableListAdapter adminsTableListAdapter;
    MaterialToolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admins_table );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        AdminsTableRecyclerView = findViewById( R.id.AdminsTableRecyclerView );
    }

    @Override
    protected void onResume() {
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
        AdminsTableRecyclerView = findViewById(R.id.AdminsTableRecyclerView);
        adminList = new ArrayList<>();
        getAdmins();
        adminsTableListAdapter = new AdminsTableListAdapter(this, adminList);
        AdminsTableRecyclerView.setAdapter(adminsTableListAdapter);
        AdminsTableRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adminsTableListAdapter.notifyDataSetChanged();
    }

    public void getAdmins() {
        Log.e("API Token", this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""));
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .admins(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""))
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e( "adminList from DB",""+adminList );
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                Admin admin = new Admin();
                                admin.setAdmin_id( Integer.parseInt( jsonObject.get( "admin_id" ).toString() ));
                                if(!jsonObject.get( "admin_mobile" ).isJsonNull()) {
                                    admin.setAdmin_mobile( jsonObject.get( "admin_mobile" ).getAsString() );}
                                else admin.setAdmin_mobile(null);
                                admin.setAdmin_email(jsonObject.get( "admin_email" ).getAsString());
                                adminList.add( admin );
                                Log.e( "adminList from DB",""+adminList );
                            }
                            adminsTableListAdapter.notifyDataSetChanged();
                        }
                        else if(jsonArray.size()==0) {
//                            Toast.makeText( DBHelper.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("onFailure",""+adminList);
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
            setIntent( new Intent( AdminsTable.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            this.getSharedPreferences("store", MODE_PRIVATE).edit().putBoolean( "guardian_logged_in", false ).commit();
            this.getSharedPreferences("store", MODE_PRIVATE).edit().putBoolean( "sponsor_logged_in", false ).commit();
            this.getSharedPreferences("store", MODE_PRIVATE).edit().putBoolean( "volunteer_logged_in", false ).commit();
            this.getSharedPreferences("store", MODE_PRIVATE).edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( AdminsTable.this, MainActivity.class ) );
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