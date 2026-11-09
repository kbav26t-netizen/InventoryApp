package com.inventory.app.data.repository

import com.inventory.app.data.db.dao.ProductDao
import com.inventory.app.data.db.entity.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) {
    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
    suspend fun getProductByBarcode(barcode: String) = productDao.getProductByBarcode(barcode)
    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)
    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
}
