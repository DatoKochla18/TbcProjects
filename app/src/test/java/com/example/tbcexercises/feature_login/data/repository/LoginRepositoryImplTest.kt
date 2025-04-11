package com.example.tbcexercises.feature_login.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.core.data.remote.utils.ApiHelper
import com.example.tbcexercises.core.domain.manager.UserSessionManager
import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.feature_login.data.remote.response.LoginResponse
import com.example.tbcexercises.feature_login.data.remote.service.LoginService
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LoginRepositoryImplTest {

    @MockK
    private lateinit var apiHelper: ApiHelper

    @MockK
    private lateinit var loginService: LoginService

    @MockK
    private lateinit var userSessionManager: UserSessionManager

    private lateinit var loginRepository: LoginRepositoryImpl

    private val email = "test@example.com"
    private val password = "password123"
    private val authRequest = AuthRequest(email = email, password = password)
    private val testToken = "test_token"
    private val loginResponse = LoginResponse(token = testToken)
    private val expectedDomainModel = GetProfileLogin(token = testToken)

    private val userTokenKey = stringPreferencesKey("user_token")
    private val userEmailKey = stringPreferencesKey("user_email")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginRepository = LoginRepositoryImpl(apiHelper, loginService, userSessionManager)
    }

    @Test
    fun `when login succeeds then result is Success`() = runTest {
        // Given
        setupSuccessfulLoginMocks()

        // When
        val result = loginRepository.login(email, password)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `when login succeeds then result contains correct data`() = runTest {
        // Given
        setupSuccessfulLoginMocks()

        // When
        val result = loginRepository.login(email, password) as Resource.Success

        // Then
        assertThat(result.data).isEqualTo(expectedDomainModel)
    }

    @Test
    fun `when login succeeds then token is saved`() = runTest {
        // Given
        setupSuccessfulLoginMocks()

        // When
        loginRepository.login(email, password)

        // Then
        coVerify(exactly = 1) { userSessionManager.saveValue(userTokenKey, testToken) }
    }

    @Test
    fun `when login succeeds then email is saved`() = runTest {
        // Given
        setupSuccessfulLoginMocks()

        // When
        loginRepository.login(email, password)

        // Then
        coVerify(exactly = 1) { userSessionManager.saveValue(userEmailKey, email) }
    }

    @Test
    fun `when login fails then result is Error`() = runTest {
        // Given
        val networkError = NetworkError.ConnectionError
        setupFailedLoginMocks(networkError)

        // When
        val result = loginRepository.login(email, password)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `when login fails then result contains correct error`() = runTest {
        // Given
        val networkError = NetworkError.ConnectionError
        setupFailedLoginMocks(networkError)

        // When
        val result = loginRepository.login(email, password) as Resource.Error

        // Then
        assertThat(result.error).isEqualTo(networkError)
    }

    @Test
    fun `when login fails then no values are saved`() = runTest {
        // Given
        setupFailedLoginMocks(NetworkError.ConnectionError)

        // When
        loginRepository.login(email, password)

        // Then
        coVerify(exactly = 0) {
            userSessionManager.saveValue<String>(any(), any())
        }
    }

    @Test
    fun `when credentials are invalid then InvalidCredentials error is returned`() = runTest {
        // Given
        setupFailedLoginMocks(NetworkError.InvalidCredentials)

        // When
        val result = loginRepository.login(email, "wrong_password") as Resource.Error

        // Then
        assertThat(result.error).isEqualTo(NetworkError.InvalidCredentials)
    }

    @Test
    fun `when user not found then UserNotFound error is returned`() = runTest {
        // Given
        setupFailedLoginMocks(NetworkError.UserNotFound)

        // When
        val result = loginRepository.login("nonexistent@example.com", password) as Resource.Error

        // Then
        assertThat(result.error).isEqualTo(NetworkError.UserNotFound)
    }

    private fun setupSuccessfulLoginMocks() {
        val response = Response.success(loginResponse)
        coEvery { loginService.login(authRequest) } returns response
        coEvery {
            apiHelper.handleNetworkRequestAsSuspend<LoginResponse>(any())
        } returns Resource.Success(loginResponse)

        coEvery { userSessionManager.saveValue(userTokenKey, testToken) } just Runs
        coEvery { userSessionManager.saveValue(userEmailKey, email) } just Runs
    }

    private fun setupFailedLoginMocks(error: NetworkError) {
        val statusCode = when (error) {
            is NetworkError.InvalidCredentials -> 401
            is NetworkError.UserNotFound -> 404
            else -> 500
        }
        val emptyResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery { loginService.login(any()) } returns Response.error(statusCode, emptyResponseBody)
        coEvery {
            apiHelper.handleNetworkRequestAsSuspend<LoginResponse>(any())
        } returns Resource.Error(error)
    }
}