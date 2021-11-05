package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class RegisterState {
    data class Error(@StringRes val textId: Int) : RegisterState()
    data class ButtonState(val isEnabled: Boolean) : RegisterState()
    object NavigateToNext : RegisterState()
    object Empty : RegisterState()
}
