package com.roque.ordenaproapp.di

import com.roque.domain.repository.ProductRepository
import com.roque.domain.usecase.product.GetCategoriesUseCase
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

    @Singleton
    @Provides
    fun provideGetProductsUseCase(productsRepository: ProductRepository): GetProductsUseCase = GetProductsUseCase(productsRepository)

    @Singleton
    @Provides
    fun provideSearchProductsUseCase(productsRepository: ProductRepository): SearchProductsUseCase = SearchProductsUseCase(productsRepository)

    @Singleton
    @Provides
    fun provideGetCategoriesUseCase(productsRepository: ProductRepository): GetCategoriesUseCase = GetCategoriesUseCase(productsRepository)

}