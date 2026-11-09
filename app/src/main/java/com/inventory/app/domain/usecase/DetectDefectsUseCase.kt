package com.inventory.app.domain.usecase

import javax.inject.Inject

class DetectDefectsUseCase @Inject constructor() {
    suspend fun detect(imageBytes: ByteArray): String? {
        return null
    }
}
