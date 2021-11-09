package com.endava.budgetplanner.authentication.ui.vm.states

import androidx.annotation.StringRes

sealed class RegisterIndustryEvent {
    data class Error(@StringRes val textId: Int) : RegisterIndustryEvent()
    object NavigateNext : RegisterIndustryEvent()
    object ConnectionError : RegisterIndustryEvent()
}