package com.kaligotla.oms.SponsorView.donation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Adopt;
import com.kaligotla.oms.SponsorView.Donate;
import com.kaligotla.oms.SponsorView.SponsorHome;

public class DonationDetails extends AppCompatActivity {
    int sid;
    Toolbar sponsor_toolbar;
    ImageView imageMobile;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_donation_details );
        recycler_view = findViewById( R.id.recycler_view );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        sid = getSharedPreferences( "store", MODE_PRIVATE ).getInt( "sid", 0 );
        //Log.e("sid000",""+getIntent().getIntExtra("sid",0));
        getSponsor( sid );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sponsor_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            startActivity( new Intent( DonationDetails.this, SponsorHome.class ) );
        } else if (item.getTitle().equals( "Donate" )) {
            startActivity( new Intent( DonationDetails.this, Donate.class ) );
        } else if (item.getTitle().equals( "Adopt" )) {
            startActivity( new Intent( DonationDetails.this, Adopt.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putInt( "sid", 0 ).commit();
            preferences.edit().putBoolean( "login_status", false ).commit();
            startActivity( new Intent( DonationDetails.this, MainActivity.class ) );
            finish();
        } else {
            finish();
            finish();
            finish();
        }
        return super.onOptionsItemSelected( item );
    }


    public void getSponsor(int id) {

    }
}