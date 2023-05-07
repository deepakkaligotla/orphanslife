package com.kaligotla.orphanslife.ui.view.volunteer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaligotla.orphanslife.databinding.ActivityVolunteerHomeBinding

class VolunteerHome : AppCompatActivity()  {

    private lateinit var binding: ActivityVolunteerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}