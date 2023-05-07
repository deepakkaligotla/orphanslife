package com.kaligotla.orphanslife.ui.view.guardian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaligotla.orphanslife.databinding.ActivityGuardianHomeBinding

class GuardianHome : AppCompatActivity()  {

    private lateinit var binding: ActivityGuardianHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuardianHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}