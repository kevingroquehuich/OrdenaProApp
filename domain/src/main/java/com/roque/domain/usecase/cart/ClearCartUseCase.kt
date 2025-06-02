package com.roque.domain.usecase.cart

import com.roque.domain.repository.CartRepository

class ClearCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke() {
        cartRepository.clearCart()
    }
}