package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.marketplace.MyApplication
import com.example.marketplace.model.NewProduct
import com.example.marketplace.model.Product
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
                products.value!![14].is_active = false
            }catch(e: Exception){
                Log.d("xxx", "ListViewMofdel exception: $e")
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
}