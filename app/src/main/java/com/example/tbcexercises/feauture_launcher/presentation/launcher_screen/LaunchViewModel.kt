package com.example.tbcexercises.feauture_launcher.presentation.launcher_screen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.core.domain.use_case.GetValueFromLocalStorageUseCase
import com.example.tbcexercises.core.presentation.util.Constants.REMEMBER_ME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase) :
    ViewModel() {

    val rememberMe = getValueFromLocalStorageUseCase(REMEMBER_ME_KEY,false)
}