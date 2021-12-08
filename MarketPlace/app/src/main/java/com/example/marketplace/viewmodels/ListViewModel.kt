package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.marketplace.MyApplication
import com.example.marketplace.model.*
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONException

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


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

    fun addProduct(newProduct : NewProduct){
        try{
            viewModelScope.launch {
                val result = repository.addProduct(MyApplication.token, newProduct)
                Log.i("result", result.toString())
            }
        } catch(e : Exception){
            if (e is HttpException) {
                val exception: HttpException = e as HttpException
                val response: retrofit2.Response<*>? = exception.response()
                try {
                    val jsonObject = JSONObject(response?.errorBody()!!.string())
                    Log.e("Error ", "" + jsonObject.optString("message"))
                } catch (e1: JSONException) {
                    e1.printStackTrace()
                    Log.e("JSONException", "exception")
                } catch (e1: IOException) {
                    e1.printStackTrace()
                    Log.e("add product IOException", "exception")
                }
            }
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
            Log.d("xxx", "LoginViewModel - updateUserData:  ${result.updated_item}")
        }
    }

    fun addOrder(addOrder : AddOrder){
        viewModelScope.launch {
            val result = repository.addOrder(MyApplication.token, addOrder)
            Log.i("result", result.toString())
        }
    }
}