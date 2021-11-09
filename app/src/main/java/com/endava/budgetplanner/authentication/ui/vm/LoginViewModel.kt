package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endava.budgetplanner.authentication.ui.vm.states.LoginEvent
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
    private val isNotEmptyValidator: MultipleValidator
) : ViewModel() {

    private var loginJob: Job? = null

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState get() = _loginState.asStateFlow()

    private val _channel = Channel<LoginEvent>()
    val channel get() = _channel.receiveAsFlow()

    fun handleFields(email: String, password: String) {
        _loginState.value = LoginState.ButtonState(isNotEmptyValidator.areValid(email, password))
    }

    fun checkFieldsValidation(email: String, password: String) {
        viewModelScope.launch {
            if (handleValidationResult(emailValidator.isValid(email)) &&
                handleValidationResult(passwordValidator.isValid(password))
            ) {
                login(email, password)
            }
        }
    }

    private suspend fun handleValidationResult(validationResult: ValidationResult): Boolean {
        return when (validationResult) {
            is ValidationResult.Error -> {
                _channel.send(LoginEvent.ValidationError(validationResult.textId))
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
            when (resource) {
                is Resource.Error -> _channel.send(LoginEvent.NetworkError(resource.messageId))
                is Resource.Success -> {
                    val token = resource.data.webToken
                    val tokenToSend = "$BEARER_PREFIX $token"
                    _channel.send(LoginEvent.NavigateNext(tokenToSend))
                }
                Resource.ConnectionError -> _channel.send(LoginEvent.ConnectionError)
            }
            _loginState.value = LoginState.Loading
        }
    }

    fun cancelJob() {
        loginJob?.cancel()
    }
}