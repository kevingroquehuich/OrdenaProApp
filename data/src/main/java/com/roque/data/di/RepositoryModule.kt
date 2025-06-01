package com.roque.data.di

import com.roque.data.repository.ProductRepositoryImpl
import com.roque.domain.datasource.remote.ProductRemoteDataSource
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
        remoteSource: ProductRemoteDataSource
    ): ProductRepositoryImpl = ProductRepositoryImpl(remoteSource)

}