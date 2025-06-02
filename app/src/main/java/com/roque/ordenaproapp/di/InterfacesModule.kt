package com.roque.ordenaproapp.di

import com.roque.data.datasource.remote.CategoryRemoteDataSourceImpl
import com.roque.data.datasource.remote.ProductRemoteDataSourceImpl
import com.roque.data.repository.ProductRepositoryImpl
import com.roque.domain.datasource.remote.CategoryRemoteDataSource
import com.roque.domain.datasource.remote.ProductRemoteDataSource
import com.roque.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
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
    abstract fun bindProductsRemoteDataSource(productRemoteDataSourceImpl: ProductRemoteDataSourceImpl): ProductRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindCategoryRemoteDataSource(categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource
}