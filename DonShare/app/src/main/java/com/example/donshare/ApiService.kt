package com.example.donshare

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @POST("users/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("users/")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("users/{id}")
    fun getUserProfile(@Path("id") id: Int): Call<UserResponse>

    @GET("event/")
    fun getAllDonasi(): Call<DonasiResponse>
}


data class UserData(
    val id: Int,
    val name: String,
    val email: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: UserData
)

data class RegisterResponse(
    val status: Int,
    val message: String,
    val data: UserData?
)

data class UserResponse(
    val status: Int,
    val message: String,
    val data: UserData
)

data class DonasiResponse(
    val status: Int,
    val message: String,
    val data: List<DonasiItem>
)

data class DonasiItem(
    val id: Int,
    val name: String,
    val image: String
)