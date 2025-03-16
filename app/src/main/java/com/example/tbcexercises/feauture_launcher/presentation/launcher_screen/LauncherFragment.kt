package com.example.tbcexercises.feauture_launcher.presentation.launcher_screen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.core.presentation.base.BaseFragment
import com.example.tbcexercises.core.presentation.extension.collectLastState
import com.example.tbcexercises.databinding.FragmentLauncherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine

@AndroidEntryPoint
class LauncherFragment : BaseFragment<FragmentLauncherBinding>(FragmentLauncherBinding::inflate) {

    private val viewModel: LaunchViewModel by viewModels()

    override fun start() {

        collectLastState(viewModel.rememberMe.combine(viewModel.token) { rememberMe, token ->
            Pair(rememberMe, token)
        }) { (rememberMe, token) ->
            when {
                !rememberMe && token.isNotEmpty() -> {
                    findNavController().navigate(
                        LauncherFragmentDirections.actionLauncherFragmentToNavigation()
                    )
                }

                token.isNotEmpty() -> {
                    findNavController().navigate(
                        LauncherFragmentDirections.actionLauncherFragmentToHomeFragment()
                    )
                }


                else -> {
                    findNavController().navigate(
                        LauncherFragmentDirections.actionLauncherFragmentToNavigation()
                    )
                }
            }
        }
    }
}