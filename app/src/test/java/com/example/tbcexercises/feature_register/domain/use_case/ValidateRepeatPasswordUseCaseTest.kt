package com.example.tbcexercises.feature_register.domain.use_case

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.RepeatPasswordError
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidateRepeatPasswordUseCaseTest {

    private val validateRepeatPasswordUseCase = ValidateRepeatPasswordUseCase()

    @Test
    fun `empty repeated password returns BLANK_FIELD error`() {
        // Given
        val password = "Password123"
        val repeatedPassword = ""

        // When
        val result = validateRepeatPasswordUseCase(password, repeatedPassword)

        // Then
        assertThat((result as Resource.Error).error).isEqualTo(RepeatPasswordError.BLANK_FIELD)
    }

    @Test
    fun `non-matching passwords returns NO_MATCH error`() {
        // Given
        val password = "Password123"
        val repeatedPassword = "Password124"

        // When
        val result = validateRepeatPasswordUseCase(password, repeatedPassword)

        // Then
        assertThat((result as Resource.Error).error).isEqualTo(RepeatPasswordError.NO_MATCH)
    }

    @Test
    fun `completely different passwords returns NO_MATCH error`() {
        // Given
        val password = "Password123"
        val repeatedPassword = "DifferentPass456"

        // When
        val result = validateRepeatPasswordUseCase(password, repeatedPassword)

        // Then
        assertThat((result as Resource.Error).error).isEqualTo(RepeatPasswordError.NO_MATCH)
    }

    @Test
    fun `case sensitive passwords returns NO_MATCH error`() {
        // Given
        val password = "Password123"
        val repeatedPassword = "password123"

        // When
        val result = validateRepeatPasswordUseCase(password, repeatedPassword)

        // Then
        assertThat((result as Resource.Error).error).isEqualTo(RepeatPasswordError.NO_MATCH)
    }

    @Test
    fun `matching passwords returns Success`() {
        // Given
        val password = "Password123"
        val repeatedPassword = "Password123"

        // When
        val result = validateRepeatPasswordUseCase(password, repeatedPassword)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }
}