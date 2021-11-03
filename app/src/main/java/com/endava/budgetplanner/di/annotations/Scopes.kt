package com.endava.budgetplanner.di.annotations

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SignInScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SplashScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class WelcomeScope