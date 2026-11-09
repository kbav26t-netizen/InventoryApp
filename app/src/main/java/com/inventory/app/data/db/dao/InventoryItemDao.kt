package com.inventory.app.data.db.dao

import androidx.room.*
import com.inventory.app.data.db.entity.InventoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {
    @Query("SELECT * FROM inventory_items ORDER BY scannedAt DESC")
    fun getAllItems(): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventory_items WHERE productId = :productId")
    fun getItemsByProduct(productId: Long): Flow<List<InventoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem): Long

    @Delete
    suspend fun deleteItem(item: InventoryItem)
}
