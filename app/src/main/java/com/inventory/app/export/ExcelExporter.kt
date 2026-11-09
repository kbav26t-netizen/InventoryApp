package com.inventory.app.export

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import javax.inject.Inject

class ExcelExporter @Inject constructor() {
    fun exportToExcel(context: Context, data: List<List<String>>): File {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Data")

        data.forEachIndexed { rowIndex, row ->
            val excelRow = sheet.createRow(rowIndex)
            row.forEachIndexed { colIndex, value ->
                excelRow.createCell(colIndex).setCellValue(value)
            }
        }

        val file = File(context.getExternalFilesDir(null), "export_${System.currentTimeMillis()}.xlsx")
        file.outputStream().use { workbook.write(it) }
        workbook.close()
        return file
    }
}
