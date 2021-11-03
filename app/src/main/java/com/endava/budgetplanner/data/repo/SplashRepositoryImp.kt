package com.endava.budgetplanner.data.repo

import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.repo.contract.SplashRepository
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SplashRepositoryImp @Inject constructor()  : SplashRepository {
    //stub to delete
    override fun initConfig() = flowOf(
        User(
            "asdfsdf",
            "fsdfsdf",
            "asdasd#12",
            "Anroid",
            "asep@mail.ru",
            1000.0
        )
    )
}