package com.kaligotla.orphanslife.ui.view.admin

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kaligotla.orphanslife.databinding.ActivityAdminHomeBinding
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModelFactory
import dagger.hilt.android.qualifiers.ApplicationContext

class AdminHome : AppCompatActivity()  {

    private lateinit var binding: ActivityAdminHomeBinding
    private val preferencesViewModel : PreferencesViewModel by viewModels {
        PreferencesViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        Log.e("Saved Key Pref",preferencesViewModel.savedKey.value.toString())
        Log.e("API Token Pref",preferencesViewModel.API_Token.value.toString())
        Log.e("ID Pref",preferencesViewModel.LoggedInUserID.value.toString())
        Log.e("Role Pref",preferencesViewModel.role.value.toString())
    }
}