package com.endava.budgetplanner.data.repo

import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    //private val authenticationService: AuthenticationService
) : AuthenticationRepository {

    override fun registerNewUser() {
        TODO("Not yet implemented")
    }

    override fun login(): String {
        return "Hello It's me"
    }
}