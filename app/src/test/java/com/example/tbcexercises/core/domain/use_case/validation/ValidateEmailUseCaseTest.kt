package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.EmailError
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidateEmailUseCaseTest {

    private val validateEmailUseCase = ValidateEmailUseCase()

    @Test
    fun `blank email returns BLANK_FIELD error`() {
        // Given
        val email = ""

        // When
        val result = validateEmailUseCase(email)

        // Then
        assertThat((result as Resource.Error).error).isEqualTo(EmailError.BLANK_FIELD)
    }

    @Test
    fun `email with only whitespace returns BLANK_FIELD error`() {
        // Given
        val email = "   "

        // When
        val result = validateEmailUseCase(email)

        assertThat((result as Resource.Error).error).isEqualTo(EmailError.BLANK_FIELD)
    }

    @Test
    fun `invalid email format returns INVALID_EMAIL error`() {
        // Given
        val email = "test@incomplete"

        // When
        val result = validateEmailUseCase(email)

        // Then
        assertThat((result as Resource.Error).error).isEqualTo(EmailError.INVALID_EMAIL)
    }

    @Test
    fun `another invalid email returns INVALID_EMAIL error`() {
        // Given
        val email = "not_an_email"

        // When
        val result = validateEmailUseCase(email)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).error).isEqualTo(EmailError.INVALID_EMAIL)
    }

    @Test
    fun `valid email returns Success`() {
        // Given
        val email = "test@example.com"

        // When
        val result = validateEmailUseCase(email)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }
}