package com.inventory.app.domain.usecase

import com.inventory.app.data.db.entity.Product
import com.inventory.app.data.repository.ProductRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(name: String, barcode: String?, category: String?): Long {
        val product = Product(name = name, barcode = barcode, category = category)
        return repository.insertProduct(product)
    }
}
