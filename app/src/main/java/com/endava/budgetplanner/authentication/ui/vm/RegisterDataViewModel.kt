package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterState
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.di.annotations.IsNotEmptyValidatorQualifier
import com.endava.budgetplanner.di.annotations.NameValidatorQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RegisterDataViewModel @Inject constructor(
    @NameValidatorQualifier
    private val nameValidator: Validator,
    @IsNotEmptyValidatorQualifier
    private val isNotEmptyValidator: MultipleValidator
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val state get() = _state.asStateFlow()

    fun handleFields(name: String, lastName: String) {
        _state.value = RegisterState.ButtonState(isNotEmptyValidator.areValid(name, lastName))
    }

    fun checkFieldsValidation(name: String, lastName: String) {
        if (handleValidationResult(nameValidator.isValid(name)) &&
            handleValidationResult(nameValidator.isValid(lastName))
        ) {
            _state.value = RegisterState.NavigateToNext
        }
    }

    private fun handleValidationResult(validationResult: ValidationResult): Boolean {
        return when (validationResult) {
            is ValidationResult.Error -> {
                _state.value = RegisterState.Error(validationResult.textId)
                false
            }
            ValidationResult.Success -> true
        }
    }
}