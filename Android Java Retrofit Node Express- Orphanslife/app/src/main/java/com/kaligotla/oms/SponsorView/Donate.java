package com.kaligotla.oms.SponsorView;

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

import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.donation.DonationDetails;

import pl.droidsonroids.gif.GifImageView;

public class Donate extends AppCompatActivity {
    Toolbar sponsor_toolbar;
    GifImageView kids_playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_donate );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        kids_playing = findViewById( R.id.kids_playing );

        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    @Override
    protected void onResume() {
        super.onResume();
        kids_playing.animate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sponsor_menu, menu );
        menu.removeItem( R.id.Logout );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            startActivity( new Intent( Donate.this, SponsorHome.class ) );
        } else if (item.getTitle().equals( "Donate" )) {
            startActivity( new Intent( Donate.this, Donate.class ) );
        } else if (item.getTitle().equals( "Adopt" )) {
            startActivity( new Intent( Donate.this, Adopt.class ) );
        } else if (item.getTitle().equals( "Home" )) {
            startActivity( new Intent( Donate.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putInt( "sid", 0 ).commit();
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( Donate.this, MainActivity.class ) );
            finish();
        } else {
            finish();
            finish();
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

    public void donate(View view) {
        startActivity( new Intent( Donate.this, DonationDetails.class ) );
    }
}