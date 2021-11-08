package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterFinalState
import com.endava.budgetplanner.common.preferences.LaunchPreferences
import com.endava.budgetplanner.common.utils.Resource
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import com.endava.budgetplanner.di.annotations.IsNotEmptyValidatorQualifier
import com.endava.budgetplanner.di.annotations.NumberValidatorQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterIndustryViewModel @Inject constructor(
    private val launchPreferences: LaunchPreferences,
    private val repository: AuthenticationRepository,
    @NumberValidatorQualifier
    private val balanceValidator: Validator,
    @IsNotEmptyValidatorQualifier
    private val isNotEmptyValidator: MultipleValidator
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterFinalState>(RegisterFinalState.Empty)
    val state get() = _state.asStateFlow()

    fun register(user: User) = viewModelScope.launch {
        _state.value = RegisterFinalState.Loading
        val resource = repository.registerNewUser(user)
        _state.value = when (resource) {
            is Resource.Error -> RegisterFinalState.Error(resource.messageId)
            is Resource.Success -> {
                setLaunchPreference()
                RegisterFinalState.NavigateToWelcome
            }
            Resource.ConnectionError -> RegisterFinalState.ConnectionError
        }
    }

    fun handleFields(initialBalance: String, industryItem: String?) {
        if (industryItem != null)
            _state.value = RegisterFinalState.ButtonState(
                isNotEmptyValidator.areValid(
                    initialBalance,
                    industryItem
                )
            )
        else
            _state.value = RegisterFinalState.ButtonState(false)
    }

    private suspend fun setLaunchPreference() {
        launchPreferences.edit { mutablePreferences ->
            mutablePreferences[LaunchPreferences.LAUNCH_KEY] = true
        }
    }
}