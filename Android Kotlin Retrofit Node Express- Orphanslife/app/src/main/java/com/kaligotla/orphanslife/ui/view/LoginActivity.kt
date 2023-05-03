package com.kaligotla.orphanslife.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kaligotla.orphanslife.databinding.ActivityLoginBinding
import com.kaligotla.orphanslife.model.LoginBody
import com.kaligotla.orphanslife.ui.viewmodel.LoginViewModel
import com.kaligotla.orphanslife.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.loginbutton.setOnClickListener {
            val email = binding.editText.text.toString()
            val password = binding.editEmail.text.toString()
            when {
                email.isEmpty() -> {
                    binding.editText.error = "Invalid Email"
                }
                password.isEmpty() -> {
                    binding.editEmail.error = "Invalid Password"
                }
                else -> {
                    val loginBody = LoginBody(email,password)
                    loginViewModel.Login(loginBody)
                }
            }
        }

        binding.validateOtpButton

    }
}