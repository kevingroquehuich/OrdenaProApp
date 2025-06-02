package com.roque.domain.usecase.cart

import com.roque.domain.model.CartItem
import com.roque.domain.repository.CartRepository

class RemoveFromCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(item: CartItem) {
        cartRepository.removeFromCart(item)
    }
}