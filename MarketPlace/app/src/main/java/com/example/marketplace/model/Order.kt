package com.example.marketplace.model
import com.squareup.moshi.JsonClass

/** Add order response **/
data class AddOrder( val title : String,
                     val description : String,
                     val price_per_unit: String,
                     val units : String,
                     val status : String,
                     val owner_username : String,
                     val revolut_link : String)

@JsonClass(generateAdapter = true)
data class AddOrderResponse(val creation : String,
                            val order_id : String,
                            val username: String,
                            val status : String,
                            val price_per_unit: String,
                            val units : String,
                            val description : String,
                            val title : String,
                            val images : List<Image>,
                            val creation_time : Long)

@JsonClass(generateAdapter = true)
data class OrderResponse(val item_count: Int, val orders: List<Order>, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class Order(val order_id : String,
                 val username : String,
                 var status : String,
                 val owner_username : String,
                 val price_per_unit: String,
                 val units : String,
                 val description : String,
                 val title : String,
                 val images : List<Image>,
                 val creation_time : Long,
                 val messages : List<String>)

@JsonClass(generateAdapter = true)
data class UpdateOrderResponse( val timestamp : UpdateOrder)

@JsonClass(generateAdapter = true)
data class UpdateOrderRequest( val status : String)

@JsonClass(generateAdapter = true)
data class UpdateOrder(val order_id : String,
                       val username : String,
                       val price_per_unit: String,
                       val units : String,
                       val description : String,
                       val title : String,
                       val status : String,
                       val creation_time : Long)