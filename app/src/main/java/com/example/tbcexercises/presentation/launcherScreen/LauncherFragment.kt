package com.example.tbcexercises.presentation.launcherScreen


import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.data.repository.UserSessionRepositoryImpl
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentLauncherBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class LauncherFragment : BaseFragment<FragmentLauncherBinding>(FragmentLauncherBinding::inflate) {

    @Inject
    lateinit var userSessionRepositoryImpl: UserSessionRepositoryImpl


    override fun start() {
        runBlocking { // i use runBlocking because it will block uiThread so it will not have affect that
            //it started HomeScreen and then went to LoginScreen
            collectLastState(userSessionRepositoryImpl.rememberMeFlow) { rememberMe ->
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
}