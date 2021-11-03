package com.endava.budgetplanner.authentication.ui.vm

import androidx.lifecycle.ViewModel
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

}