package com.renarosantos.ecommerceapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProductRepository {

    private val client = ApiClient().getClient()

    suspend fun getProductList(): List<ProductCardViewState> {
        return withContext(Dispatchers.IO) {
                client.getProdutList().map {
                    productEntity ->
                    ProductCardViewState(
                        productEntity.title,
                        productEntity.description,
                        "U$ ${productEntity.price}",
                        productEntity.imageUrl
                    )
                }
        }
    }
}