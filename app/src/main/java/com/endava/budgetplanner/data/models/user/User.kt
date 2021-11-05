package com.endava.budgetplanner.data.models.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String?,
    val lastName: String?,
    val password: String?,
    val industry: String?,
    @SerializedName("username")
    val email: String?,
    val initialBalance: Double?
) : Parcelable