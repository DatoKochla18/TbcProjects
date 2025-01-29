package com.example.tbcexercises.presentation.saveScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.User
import com.example.tbcexercises.data.datastore.ProtoDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaveScreenViewModel : ViewModel() {

    private val _saveUserState = MutableStateFlow(SaveUiState())
    val saveUserState: StateFlow<SaveUiState> = _saveUserState


    fun saveUser(firstName: String, lastName: String, email: String) {
        _saveUserState.update { it.copy(isLoading = true, isSuccessful = false, error = null) }
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
            viewModelScope.launch {
                val user = User.newBuilder().apply {
                    setEmail(email)
                    setFirstName(firstName)
                    setLastname(lastName)
                }.build()
                ProtoDataStore.saveUser(user)

                _saveUserState.update { it.copy(isLoading = false, isSuccessful =true, error = null) }
                Log.d("tag","success executed")
            }
        } else {
            Log.d("tag","error executed")

            _saveUserState.update { it.copy(isLoading = false, isSuccessful = false, error = R.string.something_went_wrong_try_again ) }
        }
    }
}