package com.renarosantos.ecommerceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState> get() = _viewState

    fun loadProductList() {
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

        viewModelScope.launch {
            _viewState.postValue(ProductListViewState.Loading)
            try {
                // Data call to fetch products
                val productList = repository.getProductList()
                _viewState.postValue(ProductListViewState.Content(productList))
            } catch (e: Exception) {
                _viewState.postValue(ProductListViewState.Error)
            }
        }

    }
    fun loadProductListWithoutCoroutines() {
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
