package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class LoginEvent {
    data class NetworkError(@StringRes val textId: Int) : LoginEvent()
    data class ValidationError(@StringRes val textId: Int) : LoginEvent()
    data class NavigateNext(val token: String) : LoginEvent()
    object ConnectionError : LoginEvent()
}