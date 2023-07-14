package com.renarosantos.ecommerceapp.data.entities

import com.google.gson.annotations.SerializedName

data class ProductEntity(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("imageUrl")
    val imageUrl: String
)