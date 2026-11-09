package com.inventory.app.domain.usecase

import com.inventory.app.data.db.entity.InventoryItem
import com.inventory.app.data.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    operator fun invoke(): Flow<List<InventoryItem>> = repository.getAllItems()
}
