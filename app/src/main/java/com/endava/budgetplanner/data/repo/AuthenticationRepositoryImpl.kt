package com.endava.budgetplanner.data.repo

import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.network.StatusCode
import com.endava.budgetplanner.common.utils.Resource
import com.endava.budgetplanner.data.api.ApiService
import com.endava.budgetplanner.data.models.user.Token
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.models.user.UserLogin
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthenticationRepository {

    override suspend fun registerNewUser(user: User): Resource<Unit> {
        val response = apiService.registerNewUser(user)
        return if (response.isSuccessful)
            Resource.Success(Unit)
        else
            Resource.Error(
                StatusCode.values().find { it.code == response.code() }?.textId
                    ?: R.string.unknown_error
            )
    }

    override suspend fun login(userLogin: UserLogin): Resource<Token> {
        val response = apiService.login(userLogin)
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(
            StatusCode.values().find { it.code == response.code() }?.textId
                ?: R.string.unknown_error
        )
    }

    //Testing method for DEMO(Will be removed)
    override suspend fun getText(token: String): Resource<String> {
        val response = apiService.getText(token)
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return if (response.code() == 500) {
            Resource.Error(R.string.invalid_password_email_error)
        } else {
            Resource.Error(R.string.unknown_error)
        }
    }
}