package com.endava.budgetplanner

import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.PasswordValidator
import com.endava.budgetplanner.models.PasswordTestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PasswordValidatorTest(private val passwordTestCase: PasswordTestCase) {

    @Test
    fun `check email validation`() {
        val case = PasswordValidator().isValid(passwordTestCase.password)
        assertEquals(passwordTestCase.expected, case)
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{0}-Password {1}-Expected")
        fun setParameters() = listOf(
            PasswordTestCase("Endava#2021", ValidationResult.Success),
            PasswordTestCase("hello", ValidationResult.Error(R.string.password_length_error)),
            PasswordTestCase(
                "EndavaEndava",
                ValidationResult.Error(R.string.password_validation_error)
            ),
            PasswordTestCase(
                "endava20211234567890123",
                ValidationResult.Error(R.string.password_length_error)
            ),
            PasswordTestCase("Endava$2021", ValidationResult.Success),
            PasswordTestCase(
                "Endava!@#$%_-",
                ValidationResult.Error(R.string.password_validation_error)
            ),
            PasswordTestCase(
                "Endava^^",
                ValidationResult.Error(R.string.password_validation_error)
            ),
            PasswordTestCase("*/*/*", ValidationResult.Error(R.string.password_length_error)),
            PasswordTestCase(
                "Endava#\$Endava",
                ValidationResult.Error(R.string.password_validation_error)
            ),
            PasswordTestCase("Endava#2021.", ValidationResult.Success)
        )
    }
}