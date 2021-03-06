package com.endava.budgetplanner.di.module

import androidx.lifecycle.ViewModel
import com.endava.budgetplanner.authentication.ui.vm.LoginViewModel
import com.endava.budgetplanner.authentication.ui.vm.RegisterDataViewModel
import com.endava.budgetplanner.authentication.ui.vm.RegisterIndustryViewModel
import com.endava.budgetplanner.authentication.ui.vm.RegisterViewModel
import com.endava.budgetplanner.di.annotations.ViewModelKey
import com.endava.budgetplanner.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BindsViewModels {

    @Binds
    @[IntoMap ViewModelKey(RegisterViewModel::class)]
    fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SplashViewModel::class)]
    fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(LoginViewModel::class)]
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(RegisterDataViewModel::class)]
    fun bindRegisterDataViewModel(viewModel: RegisterDataViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(RegisterIndustryViewModel::class)]
    fun bindRegisterIndustryViewModel(viewModel: RegisterIndustryViewModel): ViewModel
}