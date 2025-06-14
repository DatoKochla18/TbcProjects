package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.manager.ConnectivityManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConnectivity @Inject constructor(private val connectivityManager: ConnectivityManager) {

    operator fun invoke(): Flow<Boolean> {
        return connectivityManager.isConnected
    }
}