package com.inventory.app.presentation.camera

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CameraScreen(onBarcodeScanned: (String) -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Camera preview would appear here")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onBarcodeScanned("DEMO-BARCODE") }) {
            Text("Simulate Scan")
        }
    }
}
