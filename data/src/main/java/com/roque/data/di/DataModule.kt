package com.roque.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.local.CartLocalDataSource
import com.roque.data.datasource.local.dao.CartDao
import com.roque.data.datasource.local.db.OrderProDatabase
import com.roque.data.datasource.remote.ProductRemoteDataSource
import com.roque.data.repository.CartRepositoryImpl
import com.roque.domain.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OrderProDatabase {
        return Room.databaseBuilder(
            context,
            OrderProDatabase::class.java,
            "ordenapro_db"
        ).build()
    }

    @Provides
    fun provideCartDao(db: OrderProDatabase): CartDao = db.cartDao()


    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}