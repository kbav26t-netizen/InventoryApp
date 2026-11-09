package com.inventory.app.domain.usecase

import javax.inject.Inject

class ScanTextUseCase @Inject constructor() {
    suspend fun processImage(imageBytes: ByteArray): String? {
        return null
    }
}
