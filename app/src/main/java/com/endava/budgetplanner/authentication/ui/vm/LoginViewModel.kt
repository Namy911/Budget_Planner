package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endava.budgetplanner.authentication.ui.vm.states.LoginState
import com.endava.budgetplanner.common.utils.Resource
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.data.models.user.UserLogin
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import com.endava.budgetplanner.di.annotations.EmailValidatorQualifier
import com.endava.budgetplanner.di.annotations.IsNotEmptyValidatorQualifier
import com.endava.budgetplanner.di.annotations.PasswordValidatorQualifier
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val BEARER_PREFIX = "Bearer"

class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @EmailValidatorQualifier
    private val emailValidator: Validator,
    @PasswordValidatorQualifier
    private val passwordValidator: Validator,
    @IsNotEmptyValidatorQualifier
    private val isNotEmptyValidator: MultipleValidator,
) : ViewModel() {

    private var loginJob: Job? = null

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState get() = _loginState.asStateFlow()

    fun handleFields(email: String, password: String) {
        _loginState.value = LoginState.ButtonState(isNotEmptyValidator.areValid(email, password))
    }

    fun checkFieldsValidation(email: String, password: String) {
        if (handleValidationResult(emailValidator.isValid(email)) &&
            handleValidationResult(passwordValidator.isValid(password))
        ) {
            login(email, password)
        }
    }

    private fun handleValidationResult(validationResult: ValidationResult): Boolean {
        return when (validationResult) {
            is ValidationResult.Error -> {
                _loginState.value = LoginState.ValidationError(validationResult.textId)
                false
            }
            ValidationResult.Success -> true
        }
    }

    private fun login(email: String, password: String) {
        loginJob = viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val user = UserLogin(email, password)
            val resource = authenticationRepository.login(user)
            _loginState.value = when (resource) {
                is Resource.Error -> LoginState.NetworkError(resource.messageId)
                is Resource.Success -> {
                    val token = resource.data.webToken
                    val tokenToSend = "$BEARER_PREFIX $token"
                    getText(tokenToSend)
                    LoginState.Success(tokenToSend)
                }
            }
        }
    }

    //will be removed (for DEMO)
    private suspend fun getText(token: String) {
        val resource = authenticationRepository.getText(token)
        when (resource) {
            is Resource.Error -> _loginState.value = LoginState.NetworkError(resource.messageId)
            is Resource.Success -> _loginState.value = LoginState.Success(resource.data)
        }
    }

    fun cancelJob() {
        loginJob?.cancel()
    }
}