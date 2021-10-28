package com.endava.budgetplanner.models

import com.endava.budgetplanner.common.utils.ValidationResult

data class PasswordTestCase(
    val password: String,
    val expected: ValidationResult
)
