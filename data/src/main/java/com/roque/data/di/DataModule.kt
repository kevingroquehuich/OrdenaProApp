package com.roque.data.di

import com.roque.data.datasource.remote.ProductRemoteDataSourceImpl
import com.roque.data.network.api.ProductApi
import com.roque.domain.datasource.remote.ProductRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideProductsRemoteDataSourceImpl(
        productsApi: ProductApi
    ): ProductRemoteDataSourceImpl = ProductRemoteDataSourceImpl(productsApi)
}