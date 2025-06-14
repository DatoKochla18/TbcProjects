package com.example.tbcexercises.data.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ConnectivityManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : com.example.tbcexercises.domain.manager.ConnectivityManager {

    private val cm = context.getSystemService<ConnectivityManager>()!!

    override val isConnected: Flow<Boolean>
        get() = callbackFlow {
            val initial = cm.activeNetwork
                ?.let { cm.getNetworkCapabilities(it) }
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                ?: false
            trySend(initial)

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    trySend(true)
                }
                override fun onLost(network: Network) {
                    trySend(false)
                }
                override fun onCapabilitiesChanged(net: Network, caps: NetworkCapabilities) {
                    trySend(caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                }
                override fun onUnavailable() {
                    trySend(false)
                }
            }

            cm.registerDefaultNetworkCallback(callback)

            awaitClose { cm.unregisterNetworkCallback(callback) }
        }
            .distinctUntilChanged()
}
