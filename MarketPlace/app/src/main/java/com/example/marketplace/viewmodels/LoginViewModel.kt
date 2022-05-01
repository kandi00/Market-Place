package com.example.marketplace.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.*
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    private val tag = "LoginViewModel"
    var token: MutableLiveData<String> = MutableLiveData()
    val registrationCode: MutableLiveData<Int> = MutableLiveData()
    val activation: MutableLiveData<Boolean> = MutableLiveData()
    var user = MutableLiveData<User>()
    var randomUser = User()
    private lateinit var sharedPref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        val result = repository.login(request)
        token.value = result.token
        user.value!!.email = result.email
        user.value!!.phone_number = result.phone_number.toString()
        user.value!!.username = result.username
        randomUser.email = result.email
        randomUser.phone_number = result.phone_number.toString()
        randomUser.username = result.username

        /** Ads token, userName and password to shared preferences*/
        sharedPref = context.applicationContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        editor.apply {
            putString("token", token.value)
            putString("userName", result.username)
            putString("password", user.value!!.password)
            putString("phoneNumber", user.value!!.phone_number)
            putString("email", user.value!!.email)
            apply()
        }
    }

    suspend fun register(username: String, email: String, password: String, phone_number: Long) {
        val result = repository.register(RegisterRequest(username, email, password, phone_number))
        registrationCode.value = result.code
        Log.i(tag, result.message)
    }

    fun activate(username: String) {
        viewModelScope.launch {
            try {
                repository.activate(username)
                activation.value = true
                Log.i(tag, "value:  ${activation.value}")
            } catch (e: Exception) {
                activation.value = false
                Log.i(tag, "value:  ${activation.value}")
                Log.i(tag, "activate - exception: $e")
            }
        }
    }

    fun resetPassword() {
        val request =
            ResetPasswordRequest(username = user.value!!.username, email = user.value!!.email)
        viewModelScope.launch {
            try {
                val result = repository.resetPassword(request)
                Log.i(tag, result.message)
            } catch (e: Exception) {
                Log.d(tag, "resetPassword - exception: $e")
            }
        }
    }

    fun getUserInfo(userName: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserInfo(userName)
                randomUser.username = result.data[0].username
                randomUser.phone_number = result.data[0].phone_number.toString()
                randomUser.email = result.data[0].email
                Log.d(tag, "LoginViewModel - getUserInfo:  ${result.code}")
            } catch (e: Exception) {
                Log.d(tag, "LoginViewModel exception: $e")
            }
        }
    }

    fun updateUserData(userName: String, phoneNumber: String) {
        sharedPref = context.applicationContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        viewModelScope.launch {
            val result = repository.updateUserData(
                sharedPref.getString("token", null).toString(),
                UpdateUserDataRequest(phoneNumber.toLong(), userName)
            )
            /** update user data in shared preference */
            editor.apply {
                putString("token", result.updatedData.token)
                putString("userName", result.updatedData.username)
                putString("phoneNumber", result.updatedData.phone_number.toString())
                apply()
            }
            /** update random user data, because currently this is the logged in user */
            randomUser.email = result.updatedData.email
            randomUser.username = result.updatedData.username
            randomUser.phone_number = result.updatedData.phone_number.toString()
            /** update logged in user data */
            user.value!!.email = result.updatedData.email
            user.value!!.username = result.updatedData.username
            user.value!!.phone_number = result.updatedData.phone_number.toString()
            Log.d(tag, "LoginViewModel - updateUserData:  ${result.code} ${result.updatedData}")
        }
    }
}
