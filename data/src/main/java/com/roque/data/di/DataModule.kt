package com.roque.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.remote.ProductRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideProductsRemoteDataSourceImpl(
        firestore: FirebaseFirestore
    ): ProductRemoteDataSourceImpl = ProductRemoteDataSourceImpl(firestore)
}