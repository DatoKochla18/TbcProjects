package com.example.tbcexercises.presentation.contactAddEditScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.model.Contact
import com.example.tbcexercises.data.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactAddEditViewModel(private val repository: ContactRepository) : ViewModel() {

    private val _contactFlow = MutableStateFlow<Contact?>(null)
    val contactFlow: StateFlow<Contact?> = _contactFlow

    fun getContactOrNull(id: Int?) {
        if (id != null) {
            viewModelScope.launch {
                _contactFlow.value = repository.getContactById(id)
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertContact(contact)
        }
    }
}