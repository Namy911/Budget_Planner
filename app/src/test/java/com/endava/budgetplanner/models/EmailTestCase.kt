package com.endava.budgetplanner.models

import com.endava.budgetplanner.common.utils.ValidationResult

data class EmailTestCase(
    val email: String,
    val expected: ValidationResult
)
