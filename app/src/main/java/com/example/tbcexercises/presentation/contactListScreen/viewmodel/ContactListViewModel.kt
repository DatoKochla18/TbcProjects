package com.example.tbcexercises.presentation.contactListScreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.data.repository.ContactRepository

class ContactListViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    val allContact = contactRepository.contacts

}