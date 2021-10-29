package com.endava.budgetplanner.data.repo.contract

import com.endava.budgetplanner.data.models.user.User
import kotlinx.coroutines.flow.Flow

interface SplashRepository {
    //stub to delete
    fun initConfig(): Flow<User>
}