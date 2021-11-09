package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class RegisterEvent {
    object NavigateNext : RegisterEvent()
    data class Error(@StringRes val textId: Int) : RegisterEvent()
}