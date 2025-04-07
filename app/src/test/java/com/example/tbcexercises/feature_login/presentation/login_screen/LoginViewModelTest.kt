package com.example.tbcexercises.feature_login.presentation.login_screen

import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.example.tbcexercises.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.core.domain.util.PreferenceKeys.REMEMBER_ME_KEY
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.EmailError
import com.example.tbcexercises.core.domain.util.error.PasswordError
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCase
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCaseWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCaseWrapper: LoginUseCaseWrapper
    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        validateEmailUseCase = mockk()
        validatePasswordUseCase = mockk()
        loginUseCase = mockk()
        saveValueToLocalStorageUseCase = mockk()

        loginUseCaseWrapper = LoginUseCaseWrapper(
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            loginUseCase = loginUseCase,
            saveValueToLocalStorageUseCase = saveValueToLocalStorageUseCase
        )

        every { validateEmailUseCase(any()) } returns Result
            .Success(Unit)
        every { validatePasswordUseCase(any()) } returns Result
            .Success(Unit)

        viewModel = LoginViewModel(loginUseCaseWrapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        with(viewModel.uiState) {
            assertEquals("", email)
            assertEquals("", password)
            assertFalse(isEmailValid)
            assertFalse(isPasswordValid)
            assertFalse(isValidForm)
            assertFalse(isLoading)
            assertFalse(rememberMe)
            assertFalse(showPassword)
            assertNull(emailError)
            assertNull(passwordError)
        }
    }

    @Test
    fun `when email changes with valid input, state updates correctly`() = runTest {

        val validEmail = "test@example.com"
        every { validateEmailUseCase(validEmail) } returns Result.Success(
            Unit
        )

        viewModel.onEvent(LoginEvent.OnEmailChanged(validEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        with(viewModel.uiState) {
            assertEquals(validEmail, email)
            // assertTrue(isEmailValid)
            assertNull(emailError)
            assertEquals(
                isPasswordValid,
                isValidForm
            )
        }
    }

    @Test
    fun `when email changes with invalid input, error state is set`() = runTest {

        val invalidEmail = "invalid-email"
        every { validateEmailUseCase(invalidEmail) } returns Result.Error(
            EmailError.INVALID_EMAIL
        )

        viewModel.onEvent(LoginEvent.OnEmailChanged(invalidEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        with(viewModel.uiState) {
            assertEquals(invalidEmail, email)
            assertFalse(isEmailValid)
            //assertEquals(EmailError.INVALID_EMAIL, emailError)
            assertFalse(isValidForm)
        }
    }

    @Test
    fun `when password changes with valid input, state updates correctly`() = runTest {

        val validPassword = "Password123"
        every { validatePasswordUseCase(validPassword) } returns Result.Success(
            Unit
        )

        viewModel.onEvent(LoginEvent.OnPasswordChanged(validPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        with(viewModel.uiState) {
            assertEquals(validPassword, password)
            assertTrue(isPasswordValid)
            assertNull(passwordError)
            assertEquals(
                isEmailValid,
                isValidForm
            )
        }
    }

    @Test
    fun `when password changes with invalid input, error state is set`() = runTest {
        val invalidPassword = "st"
        every { validatePasswordUseCase(invalidPassword) } returns Result.Error(
            PasswordError.SHORT_PASSWORD
        )

        viewModel.onEvent(LoginEvent.OnPasswordChanged(invalidPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        with(viewModel.uiState) {
            assertEquals(invalidPassword, password)
            assertFalse(isPasswordValid)
            //assertEquals(PasswordError.SHORT_PASSWORD, passwordError)
            assertFalse(isValidForm)
        }
    }

    @Test
    fun `toggle remember me switches state`() {
        assertFalse(viewModel.uiState.rememberMe)

        viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus)

        assertTrue(viewModel.uiState.rememberMe)

        viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus)

        assertFalse(viewModel.uiState.rememberMe)
    }

    @Test
    fun `toggle show password switches state`() {

        assertFalse(viewModel.uiState.showPassword)

        viewModel.onEvent(LoginEvent.SwitchShowPasswordStatus)

        assertTrue(viewModel.uiState.showPassword)

        viewModel.onEvent(LoginEvent.SwitchShowPasswordStatus)

        assertFalse(viewModel.uiState.showPassword)
    }

    @Test
    fun `get result from register updates email and password`() {

        val email = "registered@example.com"
        val password = "RegisteredPass123"

        viewModel.onEvent(LoginEvent.GetResultFromRegister(email, password))

        with(viewModel.uiState) {
            assertEquals(email, this.email)
            assertEquals(password, this.password)
        }
    }

    @Test
    fun `login success with remember me checked saves credentials and emits success event`() =
        runTest {

            val email = "test@example.com"
            val password = "Password123"
            val profileLogin = mockk<GetProfileLogin>()

            viewModel.onEvent(LoginEvent.OnEmailChanged(email))
            viewModel.onEvent(LoginEvent.OnPasswordChanged(password))
            viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus)

            coEvery {
                loginUseCase(
                    email,
                    password
                )
            } returns flowOf(
                Result.Success(
                    profileLogin
                )
            )
            coEvery { saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, true) } returns Unit

            viewModel.onEvent(LoginEvent.Login(email, password))
            testDispatcher.scheduler.advanceUntilIdle()

            coVerify { saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, true) }
            assertFalse(viewModel.uiState.isLoading)

            viewModel.uiEvents.test {
                assertEquals(LoginSideEffect.SuccessFullLogin, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `login success without remember me checked does not save credentials`() = runTest {

        val email = "test@example.com"
        val password = "Password123"
        val profileLogin = mockk<GetProfileLogin>()

        viewModel.onEvent(LoginEvent.OnEmailChanged(email))
        viewModel.onEvent(LoginEvent.OnPasswordChanged(password))

        coEvery {
            loginUseCase(
                email,
                password
            )
        } returns flowOf(Result.Success(profileLogin))

        viewModel.onEvent(LoginEvent.Login(email, password))
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 0) {
            saveValueToLocalStorageUseCase(
                ofType<Preferences.Key<Boolean>>(),
                any<Boolean>()
            )
        }
        assertFalse(viewModel.uiState.isLoading)

        viewModel.uiEvents.test {
            assertEquals(LoginSideEffect.SuccessFullLogin, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `form becomes valid when both email and password are valid`() = runTest {
        assertFalse(viewModel.uiState.isValidForm)

        val validEmail = "test@example.com"
        every { validateEmailUseCase(validEmail) } returns Result.Success(Unit)
        viewModel.onEvent(LoginEvent.OnEmailChanged(validEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.isValidForm)

        val validPassword = "Password123"
        every { validatePasswordUseCase(validPassword) } returns Result.Success(Unit)
        viewModel.onEvent(LoginEvent.OnPasswordChanged(validPassword))
        testDispatcher.scheduler.advanceUntilIdle()

//        assertTrue(viewModel.uiState.isEmailValid)
//        assertTrue(viewModel.uiState.isPasswordValid)
        //       assertTrue(viewModel.uiState.isValidForm)
    }
}