package com.inventory.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.inventory.app.data.db.dao.InventoryItemDao
import com.inventory.app.data.db.dao.ProductDao
import com.inventory.app.data.db.entity.InventoryItem
import com.inventory.app.data.db.entity.Product

@Database(
    entities = [Product::class, InventoryItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun inventoryItemDao(): InventoryItemDao
}
