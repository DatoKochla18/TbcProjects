package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.local.DialogDao
import com.example.tbcexercises.data.model.Dialog

class DialogRepository(private val dialogDao: DialogDao) {
    suspend fun getDialogs(): List<Dialog> = dialogDao.getDialogs()
}