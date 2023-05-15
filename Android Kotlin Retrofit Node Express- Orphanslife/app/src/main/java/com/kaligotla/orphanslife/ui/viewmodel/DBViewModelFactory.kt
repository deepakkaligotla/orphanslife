package com.kaligotla.orphanslife.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaligotla.orphanslife.data.repository.DBRepo
import com.kaligotla.orphanslife.data.repository.MainRepository
import com.kaligotla.orphanslife.di.Injection

class DBViewModelFactory(private val dbRepo: DBRepo) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBViewModel::class.java)){
            return DBViewModel(dbRepo) as T
        }
        throw IllegalArgumentException("unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DBViewModelFactory? = null
        fun getInstance(context: Context): DBViewModelFactory =
            instance ?: synchronized(this){
                instance ?: DBViewModelFactory(Injection.dbRepository(context))
            }.also {
                instance = it
            }
    }
}