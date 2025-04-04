package com.example.tbcexercises

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcexercises.core.presentation.extension.getValue
import com.example.tbcexercises.core.presentation.extension.setValue
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginEvent
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginScreenRoot
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginViewModel
import com.example.tbcexercises.feature_profile.presentation.profile_screen.ProfileScreenRoot
import com.example.tbcexercises.feature_register.presentation.register_screen.RegisterRootScreen
import com.example.tbcexercises.feature_user.presentation.home_screen.HomeScreenRoot
import com.example.tbcexercises.feauture_launcher.presentation.launcher_screen.LauncherScreen
import kotlinx.serialization.Serializable


@Serializable
object Launcher

@Serializable
data class Login(val email: String?, val password: String?)

@Serializable
object Register

@Serializable
object Home

@Serializable
object Profile

@Composable
fun AppNavigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    val EMAIL = "email"
    val PASSWORD = "password"

    NavHost(
        navController = navController,
        startDestination = Launcher,
        modifier = modifier
    ) {
        composable<Launcher> {
            LauncherScreen(navigateToLoginScreen = {
                navController.navigate(
                    Login(
                        null,
                        null
                    )
                ) { popUpTo<Launcher> { inclusive = true } }
            }, navigateToHomeScreen = {
                navController.navigate(Home) { popUpTo<Launcher> { inclusive = true } }
            })
        }

        composable<Home> {
            HomeScreenRoot(navigateToProfileScreen = { navController.navigate(Profile) })
        }

        composable<Profile> {
            ProfileScreenRoot {
                navController.navigate(Login(email = null, password = null)) {
                    popUpTo<Home> {
                        inclusive = true
                    }
                }
            }
        }


        composable<Login> { entry ->
            val email = entry.getValue<String>(EMAIL)
            val password = entry.getValue<String>(PASSWORD)

            val viewModel: LoginViewModel = hiltViewModel()

            if (email != null && password != null) {
                viewModel.onEvent(LoginEvent.OnEmailChanged(email))
                viewModel.onEvent(LoginEvent.OnPasswordChanged(password))
            }
            LoginScreenRoot(
                viewModel = viewModel,
                navigateToHomeScreen = {
                    navController.navigate(Home) { popUpTo<Login> { inclusive = true } }

                },
                navigateToRegisterScreen = { navController.navigate(Register) },
                scaffoldState = scaffoldState
            )
        }

        composable<Register> {
            RegisterRootScreen(scaffoldState = scaffoldState) { email, password ->
                navController.apply {
                    previousBackStackEntry?.setValue(EMAIL, email)
                    previousBackStackEntry?.setValue(PASSWORD, password)

                }
                navController.popBackStack()
            }
        }

    }

}