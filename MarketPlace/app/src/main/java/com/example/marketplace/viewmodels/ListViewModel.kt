package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.marketplace.MyApplication
import com.example.marketplace.model.NewProduct
import com.example.marketplace.model.Product
import com.example.marketplace.model.UpdateProduct
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    var currentProductPosition : Int = 0

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${MyApplication.token}")
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getProducts(MyApplication.token)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewModel exception: $e")
            }
        }
    }

    fun getCurrentProduct(): Product {
        Log.i("pos", products.value.toString())
        return products.value?.get(currentProductPosition)!!
    }

    suspend fun addProduct(newProduct : NewProduct){
        val result = repository.addProduct(MyApplication.token, newProduct)
        Log.i("result", result.toString())
    }

    fun updateProduct(updateProduct : UpdateProduct){
        viewModelScope.launch {
            val currentProduct = getCurrentProduct()
            val result = repository.updateProduct(MyApplication.token, currentProduct.product_id, updateProduct)
            currentProduct.price_per_unit = result.updated_item.price_per_unit
            currentProduct.is_active = result.updated_item.is_active
            currentProduct.title = result.updated_item.title
            currentProduct.amount_type = result.updated_item.amount_type
            currentProduct.price_type = result.updated_item.price_type
            currentProduct.units = result.updated_item.units
            currentProduct.description = result.updated_item.description
            Log.d("xxx", "LoginViewModel - updateUserData:  ${result.updated_item}")
        }
    }
}