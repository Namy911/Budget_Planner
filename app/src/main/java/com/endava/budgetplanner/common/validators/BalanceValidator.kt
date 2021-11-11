package com.endava.budgetplanner.common.validators

import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.Validator
import javax.inject.Inject

private const val BALANCE_REGEX = "^\\d{1,5}(\\.(\\d{1,2}))?$"
private const val MIN_BALANCE = 0
private const val MAX_BALANCE = 10000

class BalanceValidator @Inject constructor() : Validator {

    override fun isValid(field: CharSequence): ValidationResult {
        val regex = BALANCE_REGEX.toRegex()
        val balance = field.toString().toDouble()
        return if (balance > MIN_BALANCE && balance < MAX_BALANCE && regex.matches(field))
            ValidationResult.Success
        else if (balance > MAX_BALANCE)
            ValidationResult.Error(R.string.number_length_error)
        else
            ValidationResult.Error(R.string.number_validation_error)
    }
}