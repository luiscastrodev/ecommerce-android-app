package com.renarosantos.ecommerceapp.wishlist.business

import com.renarosantos.ecommerceapp.wishlist.data.repository.WishlistRepository
import javax.inject.Inject

class AddOrRemoveFromWishListUseCase @Inject constructor(
    private val isProductInWishListUseCase: IsProductInWishListUseCase,
    private val wishlistRepository: WishlistRepository
) {
    suspend fun execute(productId: String) {
        if(isProductInWishListUseCase.execute(productId)){
            wishlistRepository.removeFromWishlist(productId)
        } else {
            wishlistRepository.addToWishlist(productId)
        }
    }
}