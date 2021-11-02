package com.endava.budgetplanner.data.api

import com.endava.budgetplanner.data.models.user.User
import kotlinx.coroutines.flow.Flow

interface ApiService{
    //stub to delete
    fun splashDummy(): Flow<User>
}
