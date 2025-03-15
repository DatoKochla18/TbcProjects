package com.example.tbcexercises.feature_profile.presentation.profile_screen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.core.domain.use_case.GetValueFromLocalStorageUseCase
import com.example.tbcexercises.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.tbcexercises.core.presentation.util.PreferenceKeys.EMAIL_KEY
import com.example.tbcexercises.core.presentation.util.PreferenceKeys.REMEMBER_ME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase,
    private val saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase
) :
    ViewModel() {

    val emailFlow = getValueFromLocalStorageUseCase(EMAIL_KEY, "")

    suspend fun clearUserSession() {
        withContext(NonCancellable) {
            saveValueToLocalStorageUseCase(EMAIL_KEY, "")
            saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, false)
        }
    }
}