package com.example.tbcexercises.presentation.callDialogScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.model.Contact
import com.example.tbcexercises.data.model.Dialog
import com.example.tbcexercises.data.repository.ContactRepository
import com.example.tbcexercises.data.repository.DialogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CallDialogViewModel(
    private val dialogRepository: DialogRepository,
    private val contactRepository: ContactRepository
) : ViewModel() {

    private val _dialogs = MutableStateFlow<List<Dialog>>(listOf())
    val dialogs: StateFlow<List<Dialog>> = _dialogs


    private val _contacts = MutableStateFlow<List<Contact>>(listOf())
    val contacts: StateFlow<List<Contact>> = _contacts


    init {
        getDialogs()
    }

    private fun getDialogs() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dialogRepository.getDialogs()
            _dialogs.value = result
        }
    }

    fun getContactByPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            contactRepository.getContactByPhoneNumber(phoneNumber).collectLatest { contacts ->
                _contacts.value = contacts
            }
        }
    }


}

