package com.example.tbcexercises.presentation.launcherScreen


import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentLauncherBinding
import com.example.tbcexercises.utils.exntension.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


class LauncherFragment : BaseFragment<FragmentLauncherBinding>(FragmentLauncherBinding::inflate) {
    override fun start() {
        runBlocking { // i use runBlocking because it will block uiThread so it will not have affect that
            //it started HomeScreen and then went to LoginScreen
            val rememberMe = requireContext().dataStore.data.map { it.rememberMe }.first()
            if (rememberMe) {
                findNavController().navigate(
                    LauncherFragmentDirections.actionLauncherFragmentToHomeFragment()
                )
            } else {
                findNavController().navigate(LauncherFragmentDirections.actionLauncherFragmentToNavigation())
            }
        }
    }


    override fun listeners() {
    }

}