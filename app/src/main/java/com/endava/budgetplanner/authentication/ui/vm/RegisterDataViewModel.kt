package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterEvent
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterState
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.di.annotations.IsNotEmptyValidatorQualifier
import com.endava.budgetplanner.di.annotations.NameValidatorQualifier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterDataViewModel @Inject constructor(
    @NameValidatorQualifier
    private val nameValidator: Validator,
    @IsNotEmptyValidatorQualifier
    private val isNotEmptyValidator: MultipleValidator
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val state get() = _state.asStateFlow()

    private val _channel = Channel<RegisterEvent>()
    val channel get() = _channel.receiveAsFlow()

    fun handleFields(name: String, lastName: String) {
        _state.value = RegisterState.ButtonState(isNotEmptyValidator.areValid(name, lastName))
    }

    fun checkFieldsValidation(name: String, lastName: String) {
        viewModelScope.launch {
            if (handleValidationResult(nameValidator.isValid(name)) &&
                handleValidationResult(nameValidator.isValid(lastName))
            ) {
                _channel.send(RegisterEvent.NavigateNext)
            }
        }
    }

    private suspend fun handleValidationResult(validationResult: ValidationResult): Boolean {
        return when (validationResult) {
            is ValidationResult.Error -> {
                _channel.send(RegisterEvent.Error(validationResult.textId))
                false
            }
            ValidationResult.Success -> true
        }
    }
}