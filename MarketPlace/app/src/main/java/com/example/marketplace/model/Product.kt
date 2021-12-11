package com.example.marketplace.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(val _id: String, val image_id: String, val image_name: String, val image_path: String)

@JsonClass(generateAdapter = true)
data class Product(val rating: Double,
                   var amount_type: String,
                   var price_type: String,
                   val product_id: String,
                   val username: String,
                   var is_active: Boolean,
                   var price_per_unit: String,
                   var units: String,
                   var description: String,
                   var title: String,
                   val images: List<Image>,
                   val creation_time: Long,
                   val messages : List<String>
)

@JsonClass(generateAdapter = true)
data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)

data class NewProduct(//val uploadImages: List<Image>,
                      val title: String,
                      val description: String,
                      val price_per_unit: String,
                      val units: String,
                      val is_active: Boolean,
                      val rating: Double,
                      val amount_type: String,
                      val price_type: String)

@JsonClass(generateAdapter = true)
data class AddProductResponse(val creation: String,
                              val product_id: String,
                              val username: String,
                              val is_active: Boolean,
                              val price_per_unit: String,
                              val units : String,
                              val description : String,
                              val title : String,
                              val images : List<Image>,
                              val creation_time : Long)

/** Update product request - response **/
@JsonClass(generateAdapter = true)
data class UpdateProduct( val price_per_unit: String,
                          val is_active: Boolean,
                          val title : String,
                          val amount_type: String,
                          val price_type: String,
                          val units : String,
                          val description : String)

@JsonClass(generateAdapter = true)
data class UpdateProductResponse(val updated_item : Product)