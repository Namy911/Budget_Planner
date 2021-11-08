package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class RegisterDataState {
    data class Error(@StringRes val textId: Int) : RegisterDataState()
    data class ButtonState(val isEnabled: Boolean) : RegisterDataState()
    object NavigateToNext : RegisterDataState()
    object Empty : RegisterDataState()
}
