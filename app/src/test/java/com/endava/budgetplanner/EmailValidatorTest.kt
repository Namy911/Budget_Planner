package com.endava.budgetplanner

import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.EmailValidator
import com.endava.budgetplanner.models.EmailTestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class EmailValidatorTest(private val emailTestCase: EmailTestCase) {

    @Test
    fun `check email validation`() {
        val case = EmailValidator().isValid(emailTestCase.email)
        assertEquals(emailTestCase.expected, case)
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{0}-Email {1}-Expected")
        fun setParameters() = listOf(
            EmailTestCase("user@email.com", ValidationResult.Success),
            EmailTestCase(
                "user@email,com",
                ValidationResult.Error(R.string.email_validation_error)
            ),
            EmailTestCase("us.er@email.com", ValidationResult.Success),
            EmailTestCase(
                "user@@email.com",
                ValidationResult.Error(R.string.email_validation_error)
            ),
            EmailTestCase("us_er@email.com", ValidationResult.Success),
            EmailTestCase("us-er@email.com", ValidationResult.Success),
            EmailTestCase("user133@email.com", ValidationResult.Success),
            EmailTestCase(
                "user133@emailcom",
                ValidationResult.Error(R.string.email_validation_error)
            ),
            EmailTestCase(
                "user133@email..",
                ValidationResult.Error(R.string.email_validation_error)
            ),
            EmailTestCase("us-er@email.ru", ValidationResult.Success),
            EmailTestCase("us_er@emai.ru", ValidationResult.Success),
            EmailTestCase("user@email..", ValidationResult.Error(R.string.email_validation_error)),
            EmailTestCase("user133@.gr", ValidationResult.Error(R.string.email_validation_error)),
            EmailTestCase(
                "user123.endava.com",
                ValidationResult.Error(R.string.email_validation_error)
            ),
            EmailTestCase("user123", ValidationResult.Error(R.string.email_length_error)),
            EmailTestCase(
                "user123@endavacom.",
                ValidationResult.Error(R.string.email_validation_error)
            ),
            EmailTestCase(
                "UserUserUserUserUser@mail.rutor",
                ValidationResult.Error(R.string.email_length_error)
            ),
            EmailTestCase("User@m.ru", ValidationResult.Error(R.string.email_validation_error))
        )
    }
}