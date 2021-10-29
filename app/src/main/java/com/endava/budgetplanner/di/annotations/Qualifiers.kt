package com.endava.budgetplanner.di.annotations

import android.annotation.SuppressLint
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailValidatorQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordValidatorQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NameValidatorQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IsNotEmptyValidatorQualifier