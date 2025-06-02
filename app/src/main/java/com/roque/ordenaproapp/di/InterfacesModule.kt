package com.roque.ordenaproapp.di

import com.roque.data.repository.CartRepositoryImpl
import com.roque.data.repository.OrderRepositoryImpl
import com.roque.data.repository.ProductRepositoryImpl
import com.roque.domain.repository.CartRepository
import com.roque.domain.repository.OrderRepository
import com.roque.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfacesModule {

    @Singleton
    @Binds
    abstract fun bindProductsRepository(productsRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Singleton
    @Binds
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Singleton
    @Binds
    abstract fun provideOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

}
