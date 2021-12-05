package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }

    suspend fun getUserInfo(userName : String) : UserInfoResponse{
        return RetrofitInstance.api.getUserInfo(userName)
    }

    suspend fun updateUserData(token : String, updateUserData : UpdateUserDataRequest) : UpdateUserDataResponse{
        return RetrofitInstance.api.updateUserData(token, updateUserData)
    }

    suspend fun addProduct(token: String, newProduct : NewProduct) : AddProductResponse{
        return RetrofitInstance.api.addProduct(token, newProduct)
    }

    suspend fun updateProduct(token: String, productId : String, updateProduct : UpdateProduct): UpdateProductResponse {
        return RetrofitInstance.api.updateProduct(token, productId, updateProduct)
    }

}