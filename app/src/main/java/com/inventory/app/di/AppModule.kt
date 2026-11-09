package com.inventory.app.di

import android.content.Context
import androidx.room.Room
import com.inventory.app.data.db.AppDatabase
import com.inventory.app.data.db.dao.InventoryItemDao
import com.inventory.app.data.db.dao.ProductDao
import com.inventory.app.data.repository.InventoryRepository
import com.inventory.app.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "inventory_db"
        ).build()
    }

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    fun provideInventoryItemDao(db: AppDatabase): InventoryItemDao = db.inventoryItemDao()

    @Provides
    @Singleton
    fun provideProductRepository(dao: ProductDao): ProductRepository =
        ProductRepository(dao)

    @Provides
    @Singleton
    fun provideInventoryRepository(dao: InventoryItemDao): InventoryRepository =
        InventoryRepository(dao)
}
