package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterIndustryEvent
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterIndustryState
import com.endava.budgetplanner.common.preferences.LaunchPreferences
import com.endava.budgetplanner.common.utils.Resource
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import com.endava.budgetplanner.di.annotations.IsNotEmptyValidatorQualifier
import com.endava.budgetplanner.di.annotations.NumberValidatorQualifier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val BALANCE_PREFIX = "\$"
private const val INITIAL_LENGTH = 0

class RegisterIndustryViewModel @Inject constructor(
    private val launchPreferences: LaunchPreferences,
    private val repository: AuthenticationRepository,
    @NumberValidatorQualifier
    private val balanceValidator: Validator,
    @IsNotEmptyValidatorQualifier
    private val isNotEmptyValidator: MultipleValidator
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterIndustryState>(RegisterIndustryState.Empty)
    val state get() = _state.asStateFlow()

    private val _channel = Channel<RegisterIndustryEvent>()
    val channel get() = _channel.receiveAsFlow()

    private var previousTextLength: Int = INITIAL_LENGTH

    fun handleValidationResult(balance: String, user: User) {
        viewModelScope.launch {
            val result = balanceValidator.isValid(balance)
            when (result) {
                is ValidationResult.Error -> {
                    _channel.send(
                        RegisterIndustryEvent.ValidationError(
                            result.textId
                        )
                    )
                }
                ValidationResult.Success -> register(user)
            }
        }
    }

    private suspend fun register(user: User) {
        _state.value = RegisterIndustryState.Loading
        val resource = repository.registerNewUser(user)
        when (resource) {
            is Resource.Error -> _channel.send(RegisterIndustryEvent.Error(resource.messageId))
            is Resource.Success -> {
                setLaunchPreference()
                _channel.send(RegisterIndustryEvent.NavigateNext)
            }
            Resource.ConnectionError -> _channel.send(RegisterIndustryEvent.ConnectionError)
        }
        _state.value = RegisterIndustryState.Empty
    }

    fun handleBalanceField(text: String) {
        _state.value = if (!text.startsWith(BALANCE_PREFIX) && text.isNotEmpty()) {
            if (text.length != previousTextLength) {
                previousTextLength = text.length
                RegisterIndustryState.BalanceFieldState(
                    addPrefix = true,
                    mixSize = true,
                    text = BALANCE_PREFIX.plus(text)
                )
            } else {
                RegisterIndustryState.BalanceFieldState(
                    addPrefix = true,
                    mixSize = false,
                    text = BALANCE_PREFIX.plus(text)
                )
            }
        } else {
            if (text.length != previousTextLength) {
                previousTextLength = text.length
                RegisterIndustryState.BalanceFieldState(
                    addPrefix = false,
                    mixSize = true,
                    text = text
                )
            } else {
                RegisterIndustryState.BalanceFieldState(
                    addPrefix = false,
                    mixSize = false,
                    text = text
                )
            }
        }
    }

    fun handleFields(initialBalance: String, industryItem: String?) {
        if (industryItem != null)
            _state.value = RegisterIndustryState.ButtonState(
                isNotEmptyValidator.areValid(
                    initialBalance,
                    industryItem
                )
            )
        else
            _state.value = RegisterIndustryState.ButtonState(false)
    }

    private suspend fun setLaunchPreference() {
        launchPreferences.edit { mutablePreferences ->
            mutablePreferences[LaunchPreferences.LAUNCH_KEY] = true
        }
    }
}