package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*

class Repository {
    /** login */
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun resetPassword(resetPasswordRequest : ResetPasswordRequest) : ResetPasswordResponse{
        return RetrofitInstance.api.resetPassword(resetPasswordRequest)
    }

    /** register */
    suspend fun register(registerRequest : RegisterRequest) : RegisterResponse{
        return RetrofitInstance.api.register(registerRequest)
    }

    suspend fun activate(userName : String) {
        return RetrofitInstance.api.activate(userName)
    }

    /** mange users */
    suspend fun getUserInfo(userName : String) : UserInfoResponse{
        return RetrofitInstance.api.getUserInfo(userName)
    }

    suspend fun updateUserData(token : String, updateUserData : UpdateUserDataRequest) : UpdateUserDataResponse{
        return RetrofitInstance.api.updateUserData(token, updateUserData)
    }

    /** manage products */
    suspend fun getProducts(token: String, limit : Int): ProductResponse {
        return RetrofitInstance.api.getProducts(token, limit)
    }

    suspend fun addProduct(token: String, title: String, description: String, price_per_unit: String, units: String, is_active: Boolean, rating: Double, amount_type: String, price_type: String) : AddProductResponse{
        return RetrofitInstance.api.addProduct(token, title, description, price_per_unit, units, is_active, rating, amount_type, price_type)
    }

    suspend fun updateProduct(token: String, productId : String, updateProduct : UpdateProduct): UpdateProductResponse {
        return RetrofitInstance.api.updateProduct(token, productId, updateProduct)
    }

    suspend fun removeProduct(token : String, productId : String) : RemoveProductResponse {
        return RetrofitInstance.api.removeProduct(token, productId)
    }

    /** manage orders */
    suspend fun getOrders(token: String, limit : Int): OrderResponse {
        return RetrofitInstance.api.getOrders(token, limit)
    }

    suspend fun addOrder(token: String, addOrder : AddOrder) : AddOrderResponse {
        return RetrofitInstance.api.addOrder(token, addOrder.title, addOrder.description, addOrder.price_per_unit, addOrder.units, addOrder.status, addOrder.owner_username, addOrder.revolut_link)
    }

    suspend fun updateOrder(token : String, orderId : String, updateOrderRequest: UpdateOrderRequest) : UpdateOrderResponse {
        return RetrofitInstance.api.updateOrder(token, orderId, updateOrderRequest)
    }
}