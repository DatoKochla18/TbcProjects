package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.local.ContactDao
import com.example.tbcexercises.data.model.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {
    val contacts = contactDao.getContacts()

    suspend fun upsertContact(contact: Contact) {
        contactDao.upsert(contact)
    }

    suspend fun getContactById(id: Int): Contact {
        return contactDao.getContactById(id)
    }

    fun getContactByPhoneNumber(phoneNumber: String): Flow<List<Contact>> {
        return contactDao.getContactsByPhoneNumber(phoneNumber)
    }
}