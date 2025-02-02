package com.example.tbcexercises.presentation.callDialogScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcexercises.data.repository.ContactRepository
import com.example.tbcexercises.data.repository.DialogRepository

class CallDialogViewModelFactory(
    private val dialogRepository: DialogRepository,
    private val contactRepository: ContactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CallDialogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CallDialogViewModel(dialogRepository, contactRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
