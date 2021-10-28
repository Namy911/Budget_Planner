package com.endava.budgetplanner.di.components

import android.content.Context
import com.endava.budgetplanner.authentication.ui.views.RegisterFragment
import com.endava.budgetplanner.di.module.AppModule
import com.endava.budgetplanner.di.other.MultiViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(registerFragment: RegisterFragment)

    val factories: MultiViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun create(): AppComponent
    }
}