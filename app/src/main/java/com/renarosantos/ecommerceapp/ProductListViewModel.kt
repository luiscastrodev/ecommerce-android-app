package com.renarosantos.ecommerceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductListViewModel : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState> get() = _viewState

    fun loadProductList() {

        _viewState.postValue(ProductListViewState.Loading)

        // Data call to fetch products

        val productItems = (1..3).map { product ->
            ProductCardViewState(
                "Playstation $product",
                "This is a nice console!! Check it out",
                "200 US$"
            )
        }
        val productsList = ProductListViewState.Content(productItems)

        _viewState.postValue(productsList)
    }
}