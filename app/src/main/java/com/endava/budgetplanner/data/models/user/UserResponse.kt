package com.endava.budgetplanner.data.models.user

data class UserResponse(
    val firstName: String,
    val lastName: String,
    val password: String,
    val industry: String,
    val role: String,
    val username: String,
    val initialAmount: String,
    val id: Int
)
