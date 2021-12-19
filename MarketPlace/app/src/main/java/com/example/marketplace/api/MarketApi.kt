package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.util.Constants
import com.squareup.moshi.JsonClass
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit") limit : Int): ProductResponse

    @GET(Constants.GET_USER_INFO)
    suspend fun getUserInfo(@Header("username") userName : String) : UserInfoResponse

    @POST(Constants.UPDATE_USER_DATA)
    suspend fun updateUserData(@Header("token") token: String, @Body updateUserData : UpdateUserDataRequest) : UpdateUserDataResponse

    @Multipart
    @JsonClass(generateAdapter = true)
    @POST(Constants.ADD_PRODUCT)
    suspend fun addProduct(@Header("token") token: String,
                            @Part("title") title: String,
                            @Part("description") description: String,
                            @Part("price_per_unit") price_per_unit: String,
                            @Part("units") units: String,
                            @Part("is_active") is_active: Boolean,
                            @Part("rating") rating: Double,
                            @Part("amount_type") amount_type: String,
                            @Part("price_type") price_type: String) : AddProductResponse

    @POST(Constants.UPDATE_PRODUCT)
    suspend fun updateProduct(@Header("token") token : String,
                              @Query("product_id") productId : String,
                              @Body updateProduct : UpdateProduct) : UpdateProductResponse

    @Multipart
    @POST(Constants.ADD_ORDER)
    suspend fun addOrder(@Header("token") token : String,
                         @Part("title") title : String,
                         @Part("description") description : String,
                         @Part("price_per_unit") price_per_unit: String,
                         @Part("units") units : String,
                         @Part("status") status : String,
                         @Part("owner_username") owner_username : String,
                         @Part("revolut_link") revolut_link : String) : AddOrderResponse

    @GET(Constants.GET_ORDERS)
    suspend fun getOrders(@Header("token") token: String): OrderResponse

    @POST(Constants.REMOVE_PRODUCTS)
    suspend fun removeProduct(@Header("token") token : String,
                              @Query("product_id") productId : String) : RemoveProductResponse

    @POST(Constants.UPDATE_ORDER)
    suspend fun updateOrder(@Header("token") token : String,
                            @Query("order_id") orderId : String,
                            @Body updateOrderRequest : UpdateOrderRequest) : UpdateOrderResponse
}