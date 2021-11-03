package com.endava.budgetplanner.data.repo

import com.endava.budgetplanner.R
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

    override suspend fun registerNewUser(user: User): Resource<Int> {
        val response = apiService.registerNewUser(user)
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
            return Resource.Error(R.string.no_content_error)
        }
        return when (response.code()) {
            409 -> Resource.Error(R.string.conflict_error)
            400 -> Resource.Error(R.string.bad_request_error)
            else -> Resource.Error(R.string.unknown_error)
        }
    }

    override suspend fun login(userLogin: UserLogin): Resource<Token> {
        val response = apiService.login(userLogin)
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