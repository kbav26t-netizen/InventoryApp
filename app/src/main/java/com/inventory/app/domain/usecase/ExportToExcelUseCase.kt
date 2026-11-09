package com.inventory.app.domain.usecase

import android.content.Context
import com.inventory.app.data.repository.InventoryRepository
import com.inventory.app.data.repository.ProductRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import javax.inject.Inject

class ExportToExcelUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val inventoryRepository: InventoryRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(): File = withContext(Dispatchers.IO) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Inventory")

        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Product")
        headerRow.createCell(1).setCellValue("Barcode")
        headerRow.createCell(2).setCellValue("Quantity")
        headerRow.createCell(3).setCellValue("Defect")
        headerRow.createCell(4).setCellValue("Date")

        val file = File(context.getExternalFilesDir(null), "inventory_${System.currentTimeMillis()}.xlsx")
        file.outputStream().use { workbook.write(it) }
        workbook.close()
        file
    }
}
