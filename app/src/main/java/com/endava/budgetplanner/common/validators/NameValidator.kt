package com.endava.budgetplanner.common.validators

import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.Validator
import javax.inject.Inject

private const val NAME_REGEX = "^[A-Za-z]{3,22}$"
private const val MIN_LENGTH = 3
private const val MAX_LENGTH = 22

class NameValidator @Inject constructor() : Validator {

    override fun isValid(field: CharSequence): ValidationResult {
        val regex = NAME_REGEX.toRegex()
        return if (field.length < MIN_LENGTH || field.length > MAX_LENGTH)
            ValidationResult.Error(R.string.name_length_error)
        else if (!regex.matches(field.trim()))
            ValidationResult.Error(R.string.name_validation_error)
        else
            ValidationResult.Success
    }
}