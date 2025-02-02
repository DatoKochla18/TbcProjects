package com.example.tbcexercises.presentation.contactAddEditScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcexercises.data.repository.ContactRepository


class ContactAddEditViewModelFactory(private val contactRepository: ContactRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactAddEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactAddEditViewModel(contactRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
