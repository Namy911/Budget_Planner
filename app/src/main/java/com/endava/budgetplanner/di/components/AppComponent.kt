package com.endava.budgetplanner.di.components

import android.content.Context
import com.endava.budgetplanner.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun create(): AppComponent
    }
}