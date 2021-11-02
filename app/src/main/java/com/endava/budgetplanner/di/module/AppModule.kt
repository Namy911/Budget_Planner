package com.endava.budgetplanner.di.module

import dagger.Module

@Module(includes = [BindsAppModule::class, BindsViewModels::class])
object AppModule

