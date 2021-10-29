package com.endava.budgetplanner.data.api

import com.endava.budgetplanner.data.models.user.User
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ApiServiceImp @Inject constructor(): ApiService {
    //stub to delete
    override fun splashDummy() = flowOf(
        User("user1", "surname1", "user1@email.com")
    )
}