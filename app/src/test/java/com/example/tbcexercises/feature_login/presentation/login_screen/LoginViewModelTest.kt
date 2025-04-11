package com.example.tbcexercises.feature_login.presentation.login_screen

import app.cash.turbine.test
import com.example.tbcexercises.core.domain.util.PreferenceKeys.REMEMBER_ME_KEY
import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.EmailError
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.core.domain.util.error.PasswordError
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCaseWrapper
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loginUseCaseWrapper: LoginUseCaseWrapper
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        loginUseCaseWrapper = mockk(relaxed = true)

        coEvery { loginUseCaseWrapper.validateEmailUseCase(any()) } returns Resource.Success(Unit)
        coEvery { loginUseCaseWrapper.validatePasswordUseCase(any()) } returns Resource.Success(Unit)
        coEvery { loginUseCaseWrapper.loginUseCase(any(), any()) } returns flowOf(
            Resource.Success(
                GetProfileLogin("")
            )
        )

        viewModel = LoginViewModel(loginUseCaseWrapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `OnEmailChanged event updates email in state`() = runTest {
        // Given
        val testEmail = "test@example.com"

        // When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))

        // Then
        assertThat(viewModel.uiState.email).isEqualTo(testEmail)
    }

    @Test
    fun `email validation is triggered when email changes`() = runTest {
        // Given
        val testEmail = "test@example.com"

        // When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { loginUseCaseWrapper.validateEmailUseCase(testEmail) }
    }

    @Test
    fun `successful email validation updates isEmailValid to true`() = runTest {
        // Given
        val testEmail = "test@example.com"
        coEvery { loginUseCaseWrapper.validateEmailUseCase(testEmail) } returns Resource.Success(
            Unit
        )

        // When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.isEmailValid).isTrue()
    }

    @Test
    fun `successful email validation sets emailError to null`() = runTest {
        // Given
        val testEmail = "test@example.com"
        coEvery { loginUseCaseWrapper.validateEmailUseCase(testEmail) } returns Resource.Success(
            Unit
        )

        // When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.emailError).isNull()
    }

    @Test
    fun `failed email validation updates state with error`() = runTest {
        // Given
        val testEmail = "invalid-email"
        val emailError = EmailError.INVALID_EMAIL
        coEvery { loginUseCaseWrapper.validateEmailUseCase(testEmail) } returns Resource.Error(
            emailError
        )

        // When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.emailError).isEqualTo(emailError)
    }

    @Test
    fun `failed email validation keeps isEmailValid as false`() = runTest {
        // Given
        val testEmail = "invalid-email"
        coEvery { loginUseCaseWrapper.validateEmailUseCase(testEmail) } returns Resource.Error(
            EmailError.INVALID_EMAIL
        )

        // When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.isEmailValid).isFalse()
    }

    // Password validation tests

    @Test
    fun `OnPasswordChanged event updates password in state`() = runTest {
        // Given
        val testPassword = "Password123"

        // When
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))

        // Then
        assertThat(viewModel.uiState.password).isEqualTo(testPassword)
    }

    @Test
    fun `password validation is triggered when password changes`() = runTest {
        // Given
        val testPassword = "Password123"

        // When
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { loginUseCaseWrapper.validatePasswordUseCase(testPassword) }
    }

    @Test
    fun `successful password validation updates isPasswordValid to true`() = runTest {
        // Given
        val testPassword = "Password123"
        coEvery { loginUseCaseWrapper.validatePasswordUseCase(testPassword) } returns Resource.Success(
            Unit
        )

        // When
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.isPasswordValid).isTrue()
    }

    @Test
    fun `successful password validation sets passwordError to null`() = runTest {
        // Given
        val testPassword = "Password123"
        coEvery { loginUseCaseWrapper.validatePasswordUseCase(testPassword) } returns Resource.Success(
            Unit
        )

        // When
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.passwordError).isNull()
    }

    @Test
    fun `failed password validation updates state with error`() = runTest {
        // Given
        val testPassword = "weak"
        val passwordError = PasswordError.SHORT_PASSWORD
        coEvery { loginUseCaseWrapper.validatePasswordUseCase(testPassword) } returns Resource.Error(
            passwordError
        )

        // When
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.passwordError).isEqualTo(passwordError)
    }

    @Test
    fun `failed password validation keeps isPasswordValid as false`() = runTest {
        // Given
        val testPassword = "weak"
        coEvery { loginUseCaseWrapper.validatePasswordUseCase(testPassword) } returns Resource.Error(
            PasswordError.SHORT_PASSWORD
        )

        // When
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.isPasswordValid).isFalse()
    }

    // Form validation tests

    @Test
    fun `isValidForm becomes true when both email and password are valid`() = runTest {
        // Given
        val testEmail = "test@example.com"
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        // Given
        val testPassword = "Password123"

        //when
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        assertThat(viewModel.uiState.isValidForm).isTrue()
    }

    @Test
    fun `isValidForm remains false when only email is valid`() = runTest {
        // Given
        val testEmail = "test@example.com"

        //When
        viewModel.onEvent(LoginEvent.OnEmailChanged(testEmail))
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        assertThat(viewModel.uiState.isValidForm).isFalse()
    }

    @Test
    fun `isValidForm remains false when only password is valid`() = runTest {
        // Setup valid password only
        val testPassword = "Password123"
        viewModel.onEvent(LoginEvent.OnPasswordChanged(testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert form validation
        assertThat(viewModel.uiState.isValidForm).isFalse()
    }

    // Toggle state tests

    @Test
    fun `SwitchCheckBoxStatus event toggles rememberMe from false to true`() {
        // When
        viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus)

        // Then
        assertThat(viewModel.uiState.rememberMe).isTrue()
    }

    @Test
    fun `SwitchCheckBoxStatus event toggles rememberMe from true to false`() {
        // Given
        viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus)

        // When
        viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus)

        // Then
        assertThat(viewModel.uiState.rememberMe).isFalse()
    }

    @Test
    fun `SwitchShowPasswordStatus event toggles showPassword from false to true`() {
        // When
        viewModel.onEvent(LoginEvent.SwitchShowPasswordStatus)

        // Then
        assertThat(viewModel.uiState.showPassword).isTrue()
    }

    @Test
    fun `SwitchShowPasswordStatus event toggles showPassword from true to false`() {
        // Given
        viewModel.onEvent(LoginEvent.SwitchShowPasswordStatus) // Set to true first

        // When
        viewModel.onEvent(LoginEvent.SwitchShowPasswordStatus) // Toggle back to false

        // Then
        assertThat(viewModel.uiState.showPassword).isFalse()
    }

    // Login process tests

    @Test
    fun `login sets isLoading to true immediately`() = runTest {
        // When
        viewModel.onEvent(LoginEvent.Login("test@example.com", "Password123"))

        // Then
        assertThat(viewModel.uiState.isLoading).isTrue()
    }

    @Test
    fun `login sets isLoading to false after completion`() = runTest {
        // When
        viewModel.onEvent(LoginEvent.Login("test@example.com", "Password123"))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.isLoading).isFalse()
    }

    @Test
    fun `successful login emits SuccessFullLogin side effect`() = runTest {
        // Given
        val testEmail = "test@example.com"
        val testPassword = "Password123"

        // When
        viewModel.uiEvents.test {
            viewModel.onEvent(LoginEvent.Login(testEmail, testPassword))
            testDispatcher.scheduler.advanceUntilIdle()

            //Then
            assertThat(awaitItem()).isEqualTo(LoginSideEffect.SuccessFullLogin)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `login error emits ShowSnackBar side effect`() = runTest {
        // Given
        val testEmail = "test@example.com"
        val testPassword = "wrong-password"
        val loginError = NetworkError.InvalidCredentials
        coEvery { loginUseCaseWrapper.loginUseCase(testEmail, testPassword) } returns
                flowOf(Resource.Error(loginError))

        // When
        viewModel.uiEvents.test {
            viewModel.onEvent(LoginEvent.Login(testEmail, testPassword))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            //Then
            assertThat(effect).isInstanceOf(LoginSideEffect.ShowSnackBar::class.java)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `login error side effect contains correct error message`() = runTest {
        // Given
        val testEmail = "test@example.com"
        val testPassword = "wrong-password"
        val loginError = NetworkError.InvalidCredentials
        coEvery { loginUseCaseWrapper.loginUseCase(testEmail, testPassword) } returns
                flowOf(Resource.Error(loginError))

        // When
        viewModel.uiEvents.test {
            viewModel.onEvent(LoginEvent.Login(testEmail, testPassword))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem() as LoginSideEffect.ShowSnackBar
            //Then
            assertThat(effect.message).isEqualTo(loginError.asStringResource())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `successful login with rememberMe true calls saveValueToLocalStorage`() = runTest {
        // Given
        val testEmail = "test@example.com"
        val testPassword = "Password123"
        viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus) // Enable rememberMe

        // When
        viewModel.onEvent(LoginEvent.Login(testEmail, testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { loginUseCaseWrapper.saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, true) }
    }

    @Test
    fun `successful login with rememberMe false does not call saveValueToLocalStorage`() = runTest {
        // Given
        val testEmail = "eve.holt@reqres.in"
        val testPassword = "Password123"
        // rememberMe is false by default

        // When
        viewModel.onEvent(LoginEvent.Login(testEmail, testPassword))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 0) {
            loginUseCaseWrapper.saveValueToLocalStorageUseCase(
                key = REMEMBER_ME_KEY,
                value = false
            )
        }
    }


    @Test
    fun `GetResultFromRegister event updates email state`() {
        // Given
        val testEmail = "registered@example.com"
        val testPassword = "RegisteredPass123"

        // When
        viewModel.onEvent(LoginEvent.GetResultFromRegister(testEmail, testPassword))

        // Then
        assertThat(viewModel.uiState.email).isEqualTo(testEmail)
    }

    @Test
    fun `GetResultFromRegister event updates password state`() {
        // Given
        val testEmail = "registered@example.com"
        val testPassword = "RegisteredPass123"

        // When
        viewModel.onEvent(LoginEvent.GetResultFromRegister(testEmail, testPassword))

        // Then
        assertThat(viewModel.uiState.password).isEqualTo(testPassword)
    }
}