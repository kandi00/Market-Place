package com.example.marketplace.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.UpdateUserDataRequest
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()
    var randomUser = User()

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.token = result.token
            token.value = result.token
            Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
            user.value!!.email = result.email
            user.value!!.phone_number = result.phone_number.toString()
            Log.i("user data", user.value.toString())
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: $e")
        }
    }

    fun getUserInfo(userName : String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserInfo(userName)
                randomUser.username = result.data[0].username
                randomUser.phone_number = result.data[0].phone_number.toString()
                randomUser.email = result.data[0].email
                Log.d("xxx", "LoginViewModel - getUserInfo:  ${result.code}")
            }catch(e: Exception){
                Log.d("xxx", "LoginViewModel exception: $e")
            }
        }
    }

    fun updateUserData(userName : String, phoneNumber : String){
        viewModelScope.launch {
            val result = repository.updateUserData(MyApplication.token, UpdateUserDataRequest(phoneNumber.toInt(), userName))
            MyApplication.token = result.updatedData.token
            user.value!!.email = result.updatedData.email
            user.value!!.username = result.updatedData.username
            user.value!!.phone_number = result.updatedData.phone_number.toString()
            Log.d("xxx", "LoginViewModel - updateUserData:  ${result.code} ${result.updatedData}")
        }
    }
}
