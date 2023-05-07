package com.kaligotla.orphanslife.ui.view.sponsor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaligotla.orphanslife.databinding.ActivitySponsorHomeBinding

class SponsorHome : AppCompatActivity()  {

    private lateinit var binding: ActivitySponsorHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySponsorHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}