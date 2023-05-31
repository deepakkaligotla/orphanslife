package com.kaligotla.orphanslife.ui.view.volunteer_home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kaligotla.orphanslife.R
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import com.kaligotla.orphanslife.databinding.ActivityVolunteerHomeBinding
import com.kaligotla.orphanslife.ui.view.LoginActivity
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VolunteerHome : AppCompatActivity()  {

    private lateinit var binding: ActivityVolunteerHomeBinding
    private lateinit var preferencesViewModel: PreferencesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferencesViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory(PreferencesRepo.getInstance(this))
        )[PreferencesViewModel::class.java]
        setSupportActionBar(binding.toolbarTop)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        binding.tokenPref.text = "Token - "+preferencesViewModel.API_Token
        binding.idPref.text = "ID - "+preferencesViewModel.LoggedInUserID
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu!!.removeItem(R.id.Home)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                item.isVisible = true
                finish()
                true
            }
            R.id.Home ->{
                intent = Intent(this, VolunteerHome::class.java)
                startActivity(intent)
                return true
            }
            R.id.Logout ->{
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    preferencesViewModel.setSavedKey(false)
                    preferencesViewModel.setAPI_Token("")
                    preferencesViewModel.setLoggedInUserID(0)
                    preferencesViewModel.setRole("")
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}