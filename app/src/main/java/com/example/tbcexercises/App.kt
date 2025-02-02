package com.example.tbcexercises

import android.app.Application
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.repository.ContactRepository
import com.example.tbcexercises.data.repository.DialogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())


    val appDatabase by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val dialogRepository by lazy { DialogRepository(appDatabase.dialogDao()) }
    val contactRepository by lazy { ContactRepository(appDatabase.contactDao()) }

}