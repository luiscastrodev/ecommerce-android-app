package com.renarosantos.ecommerceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState> get() = _viewState
    private val repository = ProductRepository()

    fun loadProductList() {
        viewModelScope.launch {
            _viewState.postValue(ProductListViewState.Loading)
            // Data call to fetch products
            val productList = repository.getProductList()
            _viewState.postValue(ProductListViewState.Content(productList))
        }
    }
    fun loadProductList1() {
        _viewState.postValue(ProductListViewState.Loading)
        val productItems = (1..3).map { product ->
            ProductCardViewState(
                "Playstation $product",
                "This is a nice console!! Check it out",
                "200 US$",
                "https://firebasestorage.googleapis.com/v0/b/androidecommercesample.appspot.com/o/playstation_1.png?alt=media&token=1414f40e-23cf-4f44-b922-e12bfcfca9f3"
            )
        }
        val productsList = ProductListViewState.Content(productItems)
        _viewState.postValue(productsList)
    }
}