package com.endava.budgetplanner.common.validators.contracts

import com.endava.budgetplanner.common.utils.ValidationResult

interface Validator {

    fun isValid(field: CharSequence): ValidationResult
}