package com.endava.budgetplanner.di.module

import androidx.lifecycle.ViewModelProvider
import com.endava.budgetplanner.common.validators.*
import com.endava.budgetplanner.common.validators.contracts.MultipleValidator
import com.endava.budgetplanner.common.validators.contracts.Validator
import com.endava.budgetplanner.data.repo.AuthenticationRepositoryImpl
import com.endava.budgetplanner.data.repo.SplashRepositoryImp
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import com.endava.budgetplanner.data.repo.contract.SplashRepository
import com.endava.budgetplanner.di.annotations.*
import com.endava.budgetplanner.di.other.MultiViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {

    @Binds
    fun bindViewModelFactory(factory: MultiViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    fun bindSplashRepository(imp: SplashRepositoryImp): SplashRepository

    @Binds
    @EmailValidatorQualifier
    fun bindEmailValidator(emailValidator: EmailValidator): Validator

    @Binds
    @PasswordValidatorQualifier
    fun bindPasswordValidator(passwordValidator: PasswordValidator): Validator

    @Binds
    @NameValidatorQualifier
    fun bindNameValidator(nameValidator: NameValidator): Validator

    @Binds
    @NumberValidatorQualifier
    fun bindBalanceValidator(numberValidator: BalanceValidator): Validator

    @Binds
    @IsNotEmptyValidatorQualifier
    fun bindIsNotEmptyValidator(isNotEmptyValidator: IsNotEmptyValidator): MultipleValidator
}