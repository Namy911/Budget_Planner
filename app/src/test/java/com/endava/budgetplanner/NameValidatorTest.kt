package com.endava.budgetplanner

import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.NameValidator
import com.endava.budgetplanner.models.NameTestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NameValidatorTest(private val nameTestCase: NameTestCase) {

    @Test
    fun `check name validation`() {
        val case = NameValidator().isValid(nameTestCase.name)
        assertEquals(case, nameTestCase.expected)
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{0}-Name {1}-Expected")
        fun setParameters() = listOf(
            NameTestCase("Alex", ValidationResult.Success),
            NameTestCase("Al", ValidationResult.Error(R.string.name_length_error)),
            NameTestCase(
                "UserUserUserUserUserUse",
                ValidationResult.Error(R.string.name_length_error)
            ),
            NameTestCase("UserUserUserUserUserUs", ValidationResult.Success),
            NameTestCase("USER", ValidationResult.Success),
            NameTestCase(".User.", ValidationResult.Error(R.string.name_validation_error)),
            NameTestCase("User123", ValidationResult.Error(R.string.name_validation_error)),
            NameTestCase("useR", ValidationResult.Success),
            NameTestCase("UsEr", ValidationResult.Success)
        )
    }
}