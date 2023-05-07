package com.kaligotla.orphanslife.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaligotla.orphanslife.data.repository.AdminRepo
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import com.kaligotla.orphanslife.di.Injection
import dagger.hilt.android.qualifiers.ApplicationContext

class PreferencesViewModelFactory(private val preferencesRepo: PreferencesRepo) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)){
            return PreferencesViewModel(preferencesRepo) as T
        }
        throw IllegalArgumentException("unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: PreferencesViewModelFactory? = null
        fun getInstance(context: Context): PreferencesViewModelFactory =
            instance ?: synchronized(this){
                instance ?: PreferencesViewModelFactory(Injection.prefRepository(context))
            }.also {
                instance = it
            }
    }
}