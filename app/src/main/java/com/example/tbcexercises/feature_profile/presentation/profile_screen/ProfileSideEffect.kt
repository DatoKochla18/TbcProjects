package com.example.tbcexercises.feature_profile.presentation.profile_screen

sealed interface ProfileSideEffect {
    data object OnSuccessfulLogout:ProfileSideEffect
}