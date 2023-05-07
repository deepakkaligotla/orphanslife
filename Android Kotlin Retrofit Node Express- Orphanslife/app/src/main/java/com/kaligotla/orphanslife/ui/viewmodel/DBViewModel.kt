package com.kaligotla.orphanslife.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaligotla.orphanslife.data.repository.AdminRepo
import com.kaligotla.orphanslife.model.entity.Admin
import kotlinx.coroutines.launch

class DBViewModel @ViewModelInject constructor(private val adminRepo: AdminRepo): ViewModel(){
    val admin = adminRepo.getAdminDetails
    fun newAdmin(key: Admin) {
        viewModelScope.launch {
            adminRepo.createAdmin(key)
        }
    }
}