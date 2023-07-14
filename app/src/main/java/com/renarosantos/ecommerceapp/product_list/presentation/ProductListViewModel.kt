package com.renarosantos.ecommerceapp.product_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renarosantos.ecommerceapp.shared.data.repository.ProductRepository
import com.renarosantos.ecommerceapp.wishlist.business.AddOrRemoveFromWishListUseCase
import com.renarosantos.ecommerceapp.wishlist.business.IsProductInWishListUseCase
import com.renarosantos.ecommerceapp.wishlist.data.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val isProductInWishListUseCase: IsProductInWishListUseCase,
    private val addOrRemoveFromWishListUseCase: AddOrRemoveFromWishListUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState


    fun loadProductList() {

        val productItems = (1..3).map { product ->
            ProductCardViewState(
                "",
                "Playstation $product",
                "This is a nice console!! Check it out",
                "200 US$",
                "https://firebasestorage.googleapis.com/v0/b/androidecommercesample.appspot.com/o/playstation_1.png?alt=media&token=1414f40e-23cf-4f44-b922-e12bfcfca9f3",
                false,
            )
        }
        val productsList = ProductListViewState.Content(productItems)
        _viewState.postValue(productsList)

        viewModelScope.launch {
            _viewState.postValue(ProductListViewState.Loading)
            // Data call to fetch products
            val productList = repository.getProductList()
            _viewState.postValue(
                ProductListViewState.Content(
                    productList.map {
                        ProductCardViewState(
                            it.productId,
                            it.title,
                            it.description,
                            "US $ ${it.price}",
                            it.imageUrl,
                            isProductInWishListUseCase.execute(it.productId)
                        )
                    }
                ))
        }
    }

    fun favoriteIconClicked(productId: String) {
        viewModelScope.launch {
            addOrRemoveFromWishListUseCase.execute(productId)
            val currentViewState = _viewState.value
            (currentViewState as? ProductListViewState.Content)?.let { content ->
                _viewState.postValue(
                    content.updateFavoriteProduct(
                        productId,
                        isProductInWishListUseCase.execute(productId)
                    )
                )
            }
        }
    }
}