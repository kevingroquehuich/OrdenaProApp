package com.roque.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.local.CartLocalDataSource
import com.roque.data.datasource.local.dao.CartDao
import com.roque.data.datasource.remote.ProductRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideProductsRemoteDataSource(
        firestore: FirebaseFirestore
    ): ProductRemoteDataSource = ProductRemoteDataSource(firestore)

    @Provides
    fun provideCartLocalDataSource(
        cartDao: CartDao
    ): CartLocalDataSource = CartLocalDataSource(cartDao)
}