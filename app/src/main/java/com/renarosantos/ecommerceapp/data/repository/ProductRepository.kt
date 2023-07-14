package com.renarosantos.ecommerceapp.data.repository

import com.renarosantos.ecommerceapp.presentation.viewstate.ProductCardViewState
import com.renarosantos.ecommerceapp.business.ProductDetails

interface ProductRepository {
    suspend fun getProductList():List<ProductCardViewState>
    suspend fun getProductDetails(productId : String) : ProductDetails
}