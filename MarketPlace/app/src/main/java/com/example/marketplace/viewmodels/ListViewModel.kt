package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.marketplace.MyApplication
import com.example.marketplace.model.*
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: Repository) : ViewModel() {
    var tag = "ListViewModel"
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    var orders: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    var currentProductPosition : Int = 0
    var orderId : String = ""

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${MyApplication.token}")
        getProducts()
        getOrders()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getProducts(MyApplication.token, 100)
                products.value = result.products as ArrayList<Product>
                Log.d(tag, "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d(tag, "ListViewModel exception: $e")
            }
        }
    }

    fun getCurrentProduct(): Product {
        Log.i("pos", products.value.toString())
        return products.value?.get(currentProductPosition)!!
    }

    fun getCurrentOrder() : Order {
        return orders.value?.filter { it.order_id == orderId }!![0]
    }

    fun addProduct(title: String, description: String, price_per_unit: String, units: String, is_active: Boolean, rating: Double, amount_type: String, price_type: String){
            viewModelScope.launch {
                val result = repository.addProduct(MyApplication.token, title, description, price_per_unit, units, is_active, rating, amount_type, price_type)
                Log.i("result", result.toString())
                products.value!!.add(Product(5.0, amount_type, price_type, result.product_id, result.username, is_active, price_per_unit, units, description, title, mutableListOf(), result.creation_time, mutableListOf()))
            }
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
            Log.d(tag, "LoginViewModel - updateUserData:  ${result.updated_item}")
        }
    }

    fun addOrder(addOrder : AddOrder){
        viewModelScope.launch {
            val result = repository.addOrder(MyApplication.token, addOrder)
            Log.i("result", result.toString())
            orders.value!!.add(Order(result.order_id, result.username, result.status, addOrder.owner_username, result.price_per_unit,
                result.units, result.description,  result.title, result.images, result.creation_time, mutableListOf()))
        }
    }

    private fun getOrders() {
        viewModelScope.launch {
            try {
                val result = repository.getOrders(MyApplication.token)
                orders.value = result.orders as ArrayList<Order>
                Log.d(tag, "orders:  ${result.item_count}")
            }catch(e: Exception){
                Log.d(tag, "ListViewModel orders exception: $e")
            }
        }
    }
}