package com.example.marketplace.api

import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.LoginResponse
import com.example.marketplace.model.ProductResponse
import com.example.marketplace.util.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse
}