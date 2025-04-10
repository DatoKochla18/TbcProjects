package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.PasswordError
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ValidatePasswordUseCaseTest {

    private val validatePasswordUseCase = ValidatePasswordUseCase()

    @Test
    fun `password shorter than 8 characters returns SHORT_PASSWORD error`() {
        // Given
        val password = "Abc123"

        // When
        val result = validatePasswordUseCase(password)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).error).isEqualTo(PasswordError.SHORT_PASSWORD)
    }

    @Test
    fun `password with 8 characters but only letters returns INVALID_PASSWORD error`() {
        // Given
        val password = "Abcdefgh"

        // When
        val result = validatePasswordUseCase(password)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).error).isEqualTo(PasswordError.INVALID_PASSWORD)
    }

    @Test
    fun `password with 8 characters but only digits returns INVALID_PASSWORD error`() {
        // Given
        val password = "12345678"

        // When
        val result = validatePasswordUseCase(password)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).error).isEqualTo(PasswordError.INVALID_PASSWORD)
    }

    @Test
    fun `password with 8 characters with both letters and digits returns Success`() {
        // Given
        val password = "Abc12345"

        // When
        val result = validatePasswordUseCase(password)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `longer password with both letters and digits returns Success`() {
        // Given
        val password = "SecurePassword123"

        // When
        val result = validatePasswordUseCase(password)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }
}
