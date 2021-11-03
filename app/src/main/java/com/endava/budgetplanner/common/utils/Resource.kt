package com.endava.budgetplanner.common.utils

import androidx.annotation.StringRes

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(@StringRes val messageId: Int) : Resource<Nothing>()
}