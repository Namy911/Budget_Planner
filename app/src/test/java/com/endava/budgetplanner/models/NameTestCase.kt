package com.endava.budgetplanner.models

import com.endava.budgetplanner.common.utils.ValidationResult

data class NameTestCase(
    val name: String,
    val expected: ValidationResult
)