package com.kaligotla.orphanslife.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaligotla.orphanslife.data.repository.AdminRepo
import com.kaligotla.orphanslife.di.Injection

class DBViewModelFactory(private val adminRepo: AdminRepo) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBViewModel::class.java)){
            return DBViewModel(adminRepo) as T
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