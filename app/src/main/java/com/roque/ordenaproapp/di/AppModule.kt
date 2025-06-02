package com.roque.ordenaproapp.di

import com.roque.domain.repository.CartRepository
import com.roque.domain.repository.OrderRepository
import com.roque.domain.repository.ProductRepository
import com.roque.domain.usecase.cart.AddToCartUseCase
import com.roque.domain.usecase.cart.ClearCartUseCase
import com.roque.domain.usecase.cart.GetCartUseCase
import com.roque.domain.usecase.cart.RemoveFromCartUseCase
import com.roque.domain.usecase.order.GetOrdersUseCase
import com.roque.domain.usecase.order.SaveOrderUseCase
import com.roque.domain.usecase.product.GetCategoriesUseCase
import com.roque.domain.usecase.product.GetProductByIdUseCase
import com.roque.domain.usecase.product.GetProductsUseCase
import com.roque.domain.usecase.product.SearchProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    /** PRODUCTS **/

    @Singleton
    @Provides
    fun provideGetProductsUseCase(productsRepository: ProductRepository): GetProductsUseCase = GetProductsUseCase(productsRepository)

    @Singleton
    @Provides
    fun provideGetProductByIdUseCase(productsRepository: ProductRepository): GetProductByIdUseCase = GetProductByIdUseCase(productsRepository)

    @Singleton
    @Provides
    fun provideSearchProductsUseCase(productsRepository: ProductRepository): SearchProductsUseCase = SearchProductsUseCase(productsRepository)

    @Singleton
    @Provides
    fun provideGetCategoriesUseCase(productsRepository: ProductRepository): GetCategoriesUseCase = GetCategoriesUseCase(productsRepository)

    /** CART **/

    @Singleton
    @Provides
    fun provideAddToCartUseCase(cartRepository: CartRepository): AddToCartUseCase = AddToCartUseCase(cartRepository)

    @Singleton
    @Provides
    fun provideGetCartUseCase(cartRepository: CartRepository): GetCartUseCase = GetCartUseCase(cartRepository)

    @Singleton
    @Provides
    fun provideRemoveFromCartUseCase(cartRepository: CartRepository): RemoveFromCartUseCase = RemoveFromCartUseCase(cartRepository)

    @Singleton
    @Provides
    fun provideClearCartUseCase(cartRepository: CartRepository): ClearCartUseCase = ClearCartUseCase(cartRepository)

    /** ORDER **/

    @Singleton
    @Provides
    fun provideSaveOrderUseCase(orderRepository: OrderRepository): SaveOrderUseCase = SaveOrderUseCase(orderRepository)

    @Singleton
    @Provides
    fun provideGetOrdersUseCase(orderRepository: OrderRepository): GetOrdersUseCase = GetOrdersUseCase(orderRepository)

}