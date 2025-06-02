package com.roque.domain.usecase.cart

import com.roque.domain.repository.CartRepository

class GetCartUseCase(private val cartRepository: CartRepository) {

    operator fun invoke() = cartRepository.getCart()

}