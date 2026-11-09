package com.inventory.app.presentation.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inventory.app.data.db.entity.InventoryItem

@Composable
fun InventoryScreen() {
    var items by remember { mutableStateOf<List<InventoryItem>>(emptyList()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inventory") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(items) { item ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Product ID: ${item.productId}")
                        Text("Quantity: ${item.quantity}")
                        item.defectType?.let { Text("Defect: $it") }
                    }
                }
            }
            if (items.isEmpty()) {
                item {
                    Text(
                        "No items yet. Use camera to scan.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
