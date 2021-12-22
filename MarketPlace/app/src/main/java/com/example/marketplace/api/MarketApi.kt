package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.util.Constants
import com.squareup.moshi.JsonClass
import retrofit2.http.*

interface MarketApi {
    /** login */
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.RESET_PASSWORD)
    suspend fun resetPassword(@Body resetPasswordRequest : ResetPasswordRequest)  : ResetPasswordResponse

    /** register */
    @POST(Constants.REGISTER)
    suspend fun register(@Body registerRequest : RegisterRequest): RegisterResponse

    @GET(Constants.ACTIVATE)
    suspend fun activate(@Query("username") userName : String)

    /** manage users */
    @GET(Constants.GET_USER_INFO)
    suspend fun getUserInfo(@Header("username") userName : String) : UserInfoResponse

    @POST(Constants.UPDATE_USER_DATA)
    suspend fun updateUserData(@Header("token") token: String, @Body updateUserData : UpdateUserDataRequest) : UpdateUserDataResponse

    /** manage products */
    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit") limit : Int): ProductResponse

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

    @POST(Constants.REMOVE_PRODUCTS)
    suspend fun removeProduct(@Header("token") token : String,
                              @Query("product_id") productId : String) : RemoveProductResponse

    /** manage orders */
    @GET(Constants.GET_ORDERS)
    suspend fun getOrders(@Header("token") token: String, @Header("limit") limit : Int): OrderResponse

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

    @POST(Constants.UPDATE_ORDER)
    suspend fun updateOrder(@Header("token") token : String,
                            @Query("order_id") orderId : String,
                            @Body updateOrderRequest : UpdateOrderRequest) : UpdateOrderResponse
}