package com.example.tbcexercises.presentation.launcherScreen


import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.data.local.dataStore.UserManager
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentLauncherBinding
import com.example.tbcexercises.utils.exntension.collectLastState

import kotlinx.coroutines.runBlocking


class LauncherFragment : BaseFragment<FragmentLauncherBinding>(FragmentLauncherBinding::inflate) {
    override fun start() {
        runBlocking { // i use runBlocking because it will block uiThread so it will not have affect that
            //it started HomeScreen and then went to LoginScreen
            collectLastState(UserManager.rememberMeFlow) { rememberMe ->
                if (rememberMe) {
                    findNavController().navigate(
                        LauncherFragmentDirections.actionLauncherFragmentToHomeFragment()
                    )
                } else {
                    findNavController().navigate(LauncherFragmentDirections.actionLauncherFragmentToNavigation())
                }
            }

        }
    }


    override fun listeners() {
    }

}