package com.endava.budgetplanner.data.api

import com.endava.budgetplanner.data.models.user.Token
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.models.user.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/user/registration")
    suspend fun registerNewUser(@Body user: User): Response<Int>

    @POST("api/v1/user/login")
    suspend fun login(@Body userLogin: UserLogin): Response<Token>

    @GET("api/v1/hello")
    suspend fun getText(@Header("Authorization") token: String): Response<String>
}