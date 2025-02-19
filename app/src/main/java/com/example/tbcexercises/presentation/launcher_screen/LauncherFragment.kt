package com.example.tbcexercises.presentation.launcher_screen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentLauncherBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LauncherFragment : BaseFragment<FragmentLauncherBinding>(FragmentLauncherBinding::inflate) {

    private val viewModel: LaunchViewModel by viewModels()

    override fun start() {
        runBlocking { // i use runBlocking because it will block uiThread so it will not have affect that
            //it started HomeScreen and then went to LoginScreen
            collectLastState(viewModel.rememberMe) { rememberMe ->
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