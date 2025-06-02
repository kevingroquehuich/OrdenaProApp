package com.roque.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.local.CartLocalDataSource
import com.roque.data.datasource.remote.CategoryRemoteDataSource
import com.roque.data.datasource.remote.ProductRemoteDataSource
import com.roque.data.repository.CartRepositoryImpl
import com.roque.data.repository.ProductRepositoryImpl
import com.roque.domain.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductsRepositoryImpl(
        productRemoteDataSource: ProductRemoteDataSource,
        categoryRemoteDataSource: CategoryRemoteDataSource
    ): ProductRepositoryImpl = ProductRepositoryImpl(productRemoteDataSource, categoryRemoteDataSource)


    @Provides
    @Singleton
    fun provideCartRepositoryImpl(
        cartLocalDataSource: CartLocalDataSource
    ): CartRepositoryImpl = CartRepositoryImpl(cartLocalDataSource)

}