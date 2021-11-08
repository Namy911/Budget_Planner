package com.endava.budgetplanner.data.repo

import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.network.NetworkMonitor
import com.endava.budgetplanner.common.network.StatusCode
import com.endava.budgetplanner.common.utils.Resource
import com.endava.budgetplanner.data.api.ApiService
import com.endava.budgetplanner.data.models.user.Token
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.models.user.UserLogin
import com.endava.budgetplanner.data.repo.contract.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkMonitor: NetworkMonitor
) : AuthenticationRepository {

    override suspend fun registerNewUser(user: User): Resource<Unit> {
        return if (networkMonitor.hasInternetConnection()) {
            val response = apiService.registerNewUser(user)
            if (response.isSuccessful)
                Resource.Success(Unit)
            else
                Resource.Error(
                    StatusCode.values().find { it.code == response.code() }?.textId
                        ?: R.string.unknown_error
                )
        } else Resource.ConnectionError
    }

    override suspend fun login(userLogin: UserLogin): Resource<Token> {
        return if (networkMonitor.hasInternetConnection()) {
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
        } else Resource.ConnectionError
    }
}