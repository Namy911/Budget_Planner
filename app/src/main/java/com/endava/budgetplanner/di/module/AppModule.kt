package com.endava.budgetplanner.di.module

import dagger.Module

@Module(
    includes = [
        AppBindsModule::class,
        BindsViewModels::class,
        NetworkModule::class,
        SubcomponentsModule::class
    ]
)
object AppModule