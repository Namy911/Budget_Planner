package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class RegisterFinalState {
    object NavigateToWelcome : RegisterFinalState()
    object Empty : RegisterFinalState()
    object Loading : RegisterFinalState()
    object ConnectionError : RegisterFinalState()
    data class ButtonState(val isEnabled: Boolean) : RegisterFinalState()
    data class Error(@StringRes val textId: Int) : RegisterFinalState()
}
