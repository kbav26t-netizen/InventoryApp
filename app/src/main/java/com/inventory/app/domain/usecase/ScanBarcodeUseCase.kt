package com.inventory.app.domain.usecase

import javax.inject.Inject

class ScanBarcodeUseCase @Inject constructor() {
    suspend fun processImage(imageBytes: ByteArray): String? {
        return null
    }
}
