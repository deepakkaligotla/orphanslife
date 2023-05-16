package com.kaligotla.orphanslife.ui.view.settings

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kaligotla.orphanslife.R
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import com.kaligotla.orphanslife.databinding.ActivityAdminAccountSettingsBinding
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.ui.view.LoginActivity
import com.kaligotla.orphanslife.ui.view.admin.AdminHome
import com.kaligotla.orphanslife.ui.viewmodel.DBViewModel
import com.kaligotla.orphanslife.ui.viewmodel.DBViewModelFactory
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminAccountSettings : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAccountSettingsBinding
    private lateinit var preferencesViewModel: PreferencesViewModel
    private val dbViewModel: DBViewModel by viewModels{
        DBViewModelFactory.getInstance(this)
    }
    lateinit var loggedInAdminDetails: Admin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAccountSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferencesViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory(PreferencesRepo.getInstance(applicationContext))
        )[PreferencesViewModel::class.java]
        setSupportActionBar(binding.toolbarTop)
        dbViewModel.adminDetails(preferencesViewModel.API_Token, preferencesViewModel.LoggedInUserID)
    }

    override fun onStart() {
        super.onStart()
        dbViewModel.adminDetailsById.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminAccountSettings, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                loggedInAdminDetails = Gson().fromJson(it.data.get(0), Admin::class.java)
                Toast.makeText(this@AdminAccountSettings, "OTP sent to registered email", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu!!.removeItem(R.id.donate)
        menu!!.removeItem(R.id.adopt)
        menu!!.removeItem(R.id.settings)
        menu!!.removeItem(R.id.add)
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
                intent = Intent(this, AdminHome::class.java)
                startActivity(intent)
                return true
            }
            R.id.Logout ->{
                Toast.makeText(this@AdminAccountSettings, "Logout", Toast.LENGTH_SHORT).show()
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