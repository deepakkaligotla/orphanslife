package com.kaligotla.orphanslife.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaligotla.orphanslife.data.repository.MainRepository
import com.kaligotla.orphanslife.di.Injection

class LoginViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: LoginViewModelFactory? = null
        fun getInstance(context: Context): LoginViewModelFactory =
            instance ?: synchronized(this){
                instance ?: LoginViewModelFactory(Injection.mainRepository(context))
            }.also {
                instance = it
            }
    }
}