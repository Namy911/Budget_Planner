package com.endava.budgetplanner.authentication.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import com.endava.budgetplanner.di.annotations.EmailValidatorQualifier
import com.endava.budgetplanner.di.annotations.IsNotEmptyValidatorQualifier
import com.endava.budgetplanner.di.annotations.PasswordValidatorQualifier
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @EmailValidatorQualifier
    private val emailValidator: Validator,
    @PasswordValidatorQualifier
    private val passwordValidator: Validator,
    @IsNotEmptyValidatorQualifier
    private val isNotEmptyValidator: MultipleValidator,
) : ViewModel() {

    fun login() = authenticationRepository.login()
    fun handleFields(email: String, password: String) =
        isNotEmptyValidator.areValid(email, password)

    fun checkEmailValidation(email: String) = emailValidator.isValid(email)

    fun checkPasswordValidation(password: String) = passwordValidator.isValid(password)
}