package com.roque.domain.usecase.cart

import com.roque.domain.model.CartItem
import com.roque.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartUseCase(private val cartRepository: CartRepository) {

    operator fun invoke(): Flow<List<CartItem>> = cartRepository.getCart()

}