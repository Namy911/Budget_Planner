package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class LoginState {
    data class Success(val token: String) : LoginState()
    data class NetworkError(@StringRes val textId: Int) : LoginState()
    data class ValidationError(@StringRes val textId: Int) : LoginState()
    data class ButtonState(val isEnabled: Boolean) : LoginState()
    object Empty : LoginState()
    object Loading : LoginState()
}
