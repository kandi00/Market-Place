package com.example.marketplace.model

import com.squareup.moshi.JsonClass

data class User(var username: String="", var password: String="", var email: String="", var phone_number: String="")

@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Long,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)

@JsonClass(generateAdapter = true)
data class UserInfo (
    var username: String,
    var phone_number: Long,
    var email: String
)

@JsonClass(generateAdapter = true)
data class UserInfoResponse(val code: Int, val data: List<UserInfo>, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class UpdateUserDataRequest(
    var phone_number: Long,
    var username: String
)

@JsonClass(generateAdapter = true)
data class UpdatedUserInfo (
    var username: String,
    var phone_number: Long,
    var email: String,
    var firebase_token : String,
    var is_activated: Boolean,
    var creation_time: Long,
    var token: String
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataResponse(val code : Int, val updatedData : UpdatedUserInfo, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class ResetPasswordRequest(val username : String, val email : String)

@JsonClass(generateAdapter = true)
data class ResetPasswordResponse(val code : String, val message : String, val timestamp : Long)

@JsonClass(generateAdapter = true)
data class RegisterRequest(val username : String, val email : String, val password : String, val  phone_number: Long)

@JsonClass(generateAdapter = true)
data class RegisterResponse(val code : Int, val message : String, val creation_time : Long)

@JsonClass(generateAdapter = true)
data class ActivateUserResponse(val code : Int, val message : String, val timestamp : Long)