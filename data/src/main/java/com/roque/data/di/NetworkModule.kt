package com.roque.data.di

import com.roque.data.BuildConfig
import com.roque.data.network.ApiClient
import com.roque.data.network.HeaderInterceptor
import com.roque.data.network.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()

    @Singleton
    @Provides
    fun provideClient(headerInterceptor: HeaderInterceptor): Retrofit {
        return ApiClient.create(BuildConfig.BASE_URL, headerInterceptor)
    }

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)
}