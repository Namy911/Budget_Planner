package com.endava.budgetplanner.data.models.user

import com.google.gson.annotations.SerializedName

data class User(
    val firstName: String,
    val lastName: String,
    val password: String,
    val industry: String,
    @SerializedName("username")
    val email: String,
    val initialBalance: Double
)