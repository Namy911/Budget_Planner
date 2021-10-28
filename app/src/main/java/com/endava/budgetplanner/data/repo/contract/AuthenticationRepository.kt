package com.endava.budgetplanner.data.repo.contract

interface AuthenticationRepository {

    fun registerNewUser()

    fun login(): String
}