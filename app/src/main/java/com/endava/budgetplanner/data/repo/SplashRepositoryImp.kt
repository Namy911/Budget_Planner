package com.endava.budgetplanner.data.repo

import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.repo.contract.SplashRepository
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SplashRepositoryImp @Inject constructor()  : SplashRepository {
    //stub to delete
    override fun initConfig() = flowOf(
        User("user1", "surname1", "user1@email.com")
    )
}