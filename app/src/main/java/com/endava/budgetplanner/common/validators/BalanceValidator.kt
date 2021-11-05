package com.endava.budgetplanner.common.validators

import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.Validator
import javax.inject.Inject

private const val EMAIL_REGEX = "^[0-9]{5}$"
private const val MAX_LENGTH = 5

class BalanceValidator @Inject constructor() : Validator {

    override fun isValid(field: CharSequence): ValidationResult {
        val regex = EMAIL_REGEX.toRegex()
        return if (field.length > MAX_LENGTH)
            ValidationResult.Error(R.string.number_length_error)
        else if (!regex.matches(field.trim()))
            ValidationResult.Error(R.string.number_validation_error)
        else ValidationResult.Success
    }
}