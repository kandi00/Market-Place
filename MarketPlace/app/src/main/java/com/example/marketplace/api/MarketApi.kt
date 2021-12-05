package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.util.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse

    @GET(Constants.GET_USER_INFO)
    suspend fun getUserInfo(@Header("username") userName : String) : UserInfoResponse

    @POST(Constants.UPDATE_USER_DATA)
    suspend fun updateUserData(@Header("token") token: String, @Body updateUserData : UpdateUserDataRequest) : UpdateUserDataResponse

    @POST(Constants.ADD_PRODUCT)
    suspend fun addProduct(@Header("token") token: String,  @Body newProduct : NewProduct) : AddProductResponse

    @POST(Constants.UPDATE_PRODUCT)
    suspend fun updateProduct(@Header("token") token : String,
                              @Query("product_id") productId : String,
                              @Body updateProduct : UpdateProduct) : UpdateProductResponse
}