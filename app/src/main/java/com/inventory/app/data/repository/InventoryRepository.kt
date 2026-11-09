package com.inventory.app.data.repository

import com.inventory.app.data.db.dao.InventoryItemDao
import com.inventory.app.data.db.entity.InventoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepository @Inject constructor(
    private val inventoryItemDao: InventoryItemDao
) {
    fun getAllItems(): Flow<List<InventoryItem>> = inventoryItemDao.getAllItems()
    fun getItemsByProduct(productId: Long) = inventoryItemDao.getItemsByProduct(productId)
    suspend fun insertItem(item: InventoryItem) = inventoryItemDao.insertItem(item)
    suspend fun deleteItem(item: InventoryItem) = inventoryItemDao.deleteItem(item)
}
