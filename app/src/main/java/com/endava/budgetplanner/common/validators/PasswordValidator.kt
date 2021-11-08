package com.endava.budgetplanner.common.validators

import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.Validator
import javax.inject.Inject

private const val PASSWORD_REGEX =
    "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[*.!@\$%#^&?~_|]).{8,22}\$"
private const val MIN_LENGTH = 8
private const val MAX_LENGTH = 22

class PasswordValidator @Inject constructor() : Validator {

    override fun isValid(field: CharSequence): ValidationResult {
        val regex = PASSWORD_REGEX.toRegex()
        return if ((field.length < MIN_LENGTH || field.length > MAX_LENGTH)
            || !regex.matches(field.trim())
        )
            ValidationResult.Error(R.string.password_validation_error)
        else
            ValidationResult.Success
    }
}