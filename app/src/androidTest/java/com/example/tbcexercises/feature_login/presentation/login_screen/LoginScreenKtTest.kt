package com.example.tbcexercises.feature_login.presentation.login_screen

//@ExperimentalCoroutinesApi
//@ExperimentalTime
//class LoginScreenIntegrationTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    private val testDispatcher = StandardTestDispatcher()
//    private lateinit var viewModel: LoginViewModel
//    private lateinit var scaffoldState: SnackbarHostState
//    private val uiEvents = MutableSharedFlow<LoginSideEffect>()
//    private val navigateToRegisterScreen = mockk<() -> Unit>(relaxed = true)
//    private val navigateToHomeScreen = mockk<() -> Unit>(relaxed = true)
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        scaffoldState = SnackbarHostState()
//        viewModel = mockk(relaxed = true)
//
//        // Mock ViewModel behavior
//        every { viewModel.uiEvents } returns uiEvents
//        every { viewModel.uiState } returns LoginUiState(
//            email = "",
//            password = "",
//            showPassword = false,
//            rememberMe = false,
//            isLoading = false,
//            emailError = null,
//            passwordError = null,
//            isValidForm = false
//        )
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun loginScreen_initialState_displaysCorrectly() {
//        // When
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = viewModel.uiState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // Then
//        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Remember me").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Don't have account? Sign up").assertIsDisplayed()
//    }
//
//    @Test
//    fun loginScreen_enterCredentials_triggersEvents() {
//        // Given
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = viewModel.uiState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // When
//        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
//        composeTestRule.onNodeWithText("Password").performTextInput("password123")
//
//        // Then
//        verify { viewModel.onEvent(LoginEvent.OnEmailChanged("test@example.com")) }
//        verify { viewModel.onEvent(LoginEvent.OnPasswordChanged("password123")) }
//    }
//
//    @Test
//    fun loginScreen_clickRememberMe_triggersEvent() {
//        // Given
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = viewModel.uiState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // When
//        composeTestRule.onNodeWithText("Remember me").performClick()
//
//        // Then
//        verify { viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus) }
//    }
//
//    @Test
//    fun loginScreen_clickLogin_triggersLoginEvent() {
//        // Given
//        val validState = LoginUiState(
//            email = "test@example.com",
//            password = "password123",
//            showPassword = false,
//            rememberMe = true,
//            isLoading = false,
//            emailError = null,
//            passwordError = null,
//            isValidForm = true
//        )
//
//        every { viewModel.uiState } returns validState
//
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = validState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // When
//        composeTestRule.onNodeWithText("Login").performClick()
//
//        // Then
//        verify { viewModel.onEvent(LoginEvent.Login("test@example.com", "password123")) }
//    }
//
//    @Test
//    fun loginScreen_clickSignUp_navigatesToRegister() {
//        // Given
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = viewModel.uiState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // When
//        composeTestRule.onNodeWithText("Don't have account? Sign up").performClick()
//
//        // Then
//        verify { navigateToRegisterScreen() }
//    }
//
//    @Test
//    fun loginScreen_successfulLogin_navigatesToHome() = runTest {
//        // Given
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = viewModel.uiState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // When
//        uiEvents.emit(LoginSideEffect.SuccessFullLogin)
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // Then
//        verify { navigateToHomeScreen() }
//    }
//
//    @Test
//    fun loginScreen_showError_displaysSnackbar() = runTest {
//        // Given
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = viewModel.uiState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // When
//        uiEvents.emit(LoginSideEffect.ShowSnackBar(R.string.invalid_credintials))
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // Then - This is checking internal state since we can't directly test the snackbar in this test
//        // In a real test, you might use a real context and verify the exact string
//        assert(scaffoldState.currentSnackbarData != null)
//    }
//
//    @Test
//    fun loginScreen_loading_showsProgressIndicator() {
//        // Given
//        val loadingState = LoginUiState(
//            email = "",
//            password = "",
//            showPassword = false,
//            rememberMe = false,
//            isLoading = true,
//            emailError = null,
//            passwordError = null,
//            isValidForm = false
//        )
//
//        every { viewModel.uiState } returns loadingState
//
//        // When
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = loadingState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // Then
//        composeTestRule.onNodeWithTag("CircularProgressIndicator").assertExists()
//        composeTestRule.onNodeWithText("Email").assertDoesNotExist()
//    }
//
//    @Test
//    fun loginScreen_errorState_showsErrorText() {
//        // Given
//        val errorState = LoginUiState(
//            email = "invalid",
//            password = "123",
//            showPassword = false,
//            rememberMe = false,
//            isLoading = false,
//            emailError = EmailError.INVALID_EMAIL,
//            passwordError = PasswordError.SHORT_PASSWORD,
//            isValidForm = false
//        )
//
//        every { viewModel.uiState } returns errorState
//
//        // When
//        composeTestRule.setContent {
//            LoginScreen(
//                uiState = errorState,
//                onEvent = viewModel::onEvent,
//                uiEvents = uiEvents,
//                navigateToRegisterScreen = navigateToRegisterScreen,
//                navigateToHomeScreen = navigateToHomeScreen,
//                scaffoldState = scaffoldState
//            )
//        }
//
//        // Then - Check if error messages exist
//        // Note: In a real test you'd use actual strings from R.string resources
//        composeTestRule.onNodeWithText("Invalid email format").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Password is too short").assertIsDisplayed()
//    }
//}