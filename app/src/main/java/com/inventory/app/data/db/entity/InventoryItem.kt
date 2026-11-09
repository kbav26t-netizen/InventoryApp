package com.inventory.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val quantity: Int,
    val defectType: String? = null,
    val defectImageUri: String? = null,
    val scannedAt: Long = System.currentTimeMillis()
)
