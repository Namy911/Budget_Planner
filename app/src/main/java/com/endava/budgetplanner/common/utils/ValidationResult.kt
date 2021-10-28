package com.endava.budgetplanner.common.utils

import androidx.annotation.StringRes

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(@StringRes val textId: Int) : ValidationResult()
}
