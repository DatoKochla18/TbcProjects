package com.example.tbcexercises.feature_profile.presentation.profile_screen

sealed interface ProfileEvent {

    data object OnLogOutClicked : ProfileEvent
    data object GetEmail : ProfileEvent
}