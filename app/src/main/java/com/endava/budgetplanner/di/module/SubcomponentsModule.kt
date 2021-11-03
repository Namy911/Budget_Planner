package com.endava.budgetplanner.di.module

import com.endava.budgetplanner.authentication.di.components.LoginComponent
import com.endava.budgetplanner.authentication.di.components.RegisterComponent
import com.endava.budgetplanner.authentication.di.components.WelcomeComponent
import com.endava.budgetplanner.splash.di.SplashComponent
import dagger.Module

@Module(
    subcomponents = [
        RegisterComponent::class,
        LoginComponent::class,
        SplashComponent::class,
        WelcomeComponent::class
    ]
)
object SubcomponentsModule