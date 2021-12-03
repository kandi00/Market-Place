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
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)

@JsonClass(generateAdapter = true)
data class UserInfo (
    var username: String,
    var phone_number: Int,
    var email: String,
    var firebase_token : String,
    var is_activated: Boolean,
    var creation_time: Long
)

@JsonClass(generateAdapter = true)
data class UserInfoResponse(val code: Int, val data: List<UserInfo>, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class UpdateUserDataRequest(
    var phone_number: Int,
    var username: String
)

@JsonClass(generateAdapter = true)
data class UpdatedUserInfo (
    var username: String,
    var phone_number: Int,
    var email: String,
    var firebase_token : String,
    var is_activated: Boolean,
    var creation_time: Long,
    var token: String
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataResponse(val code : Int, val updatedData : UpdatedUserInfo, val timestamp: Long)