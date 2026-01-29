package com.example.donshare

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //Auth
    @POST("users/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("users/")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("users/{id}")
    fun getUserProfile(@Path("id") id: Int): Call<UserResponse>

    //Donasi
    @GET("event/")
    fun getAllDonasi(): Call<DonasiResponse>

    @POST("donasi/")
    fun createDonasi(@Body request: DonasiRequest): Call<CreateDonasiResponse>

    @GET("donasi/user/{id}")
    fun getHistoryDonasi(@Path("id") id: Int): Call<HistoryResponse>

    //Payment Method
    @GET("payment/method/")
    fun getAllPaymentMethod(): Call<PaymentMethodResponse>

    @GET("payment/method/{id}")
    fun getPaymentMethodById(@Path("id") id: Int): Call<PaymentMethodDetailResponse>

    @GET("payment/user/{id}")
    fun getUserPayments(@Path("id") id: Int): Call<UserPaymentResponse>

    @POST("payment/user/")
    fun createUserPayment(
        @Body request: CreateUserPaymentRequest
    ): Call<CreateUserPaymentResponse>
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
    val image: String?,
    @SerializedName("total_amount")
    val totalAmount: String?
)

data class DonasiRequest(
    val user_id: Int,
    val event_id: Int,
    val user_payment_id: Int,
    val amount: Long
)

data class CreateDonasiResponse(
    val status: Int,
    val message: String,
    val data: Any?
)

data class PaymentMethodResponse(
    val status: Int,
    val message: String,
    val data: List<PaymentMethodItem>
)

data class PaymentMethodDetailResponse(
    val status: Int,
    val message: String,
    val data: PaymentMethodItem
)

data class PaymentMethodItem(
    val id: Int,
    val name: String,
    val image: String?
)

data class UserPaymentResponse(
    val status: Int,
    val message: String,
    val data: List<UserPaymentItem>
)

data class UserPaymentItem(
    val id: Int,
    val nomor: String,
    @SerializedName("payment_method")
    val paymentMethod: PaymentMethodItem
)

data class CreateUserPaymentRequest(
    val user_id: Int,
    val payment_method_id: Int,
    val nomor: String
)

data class CreateUserPaymentResponse(
    val status: Int,
    val message: String,
    val data: UserPaymentItem?
)

data class HistoryResponse(
    val status: Int,
    val message: String,
    val data: HistoryData
)

data class HistoryData(
    val user: UserInfo,
    val donations: List<DonationHistoryItem>
)

data class UserInfo(
    val name: String
)

data class DonationHistoryItem(
    val id: Int,
    val amount: String,
    val event: HistoryEvent,
    val payment: HistoryPayment
)

data class HistoryEvent(
    val name: String,
    val image: String
)

data class HistoryPayment(
    val method: String,
    val number: String
)
