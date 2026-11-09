package com.inventory.app.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inventory.app.data.db.entity.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    Scaffold(topBar = { TopAppBar(title = { Text("Products") }) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            items(products) { product ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(product.name, style = MaterialTheme.typography.titleMedium)
                        product.barcode?.let { Text("Barcode: $it") }
                        product.category?.let { Text("Category: $it") }
                    }
                }
            }
            if (products.isEmpty()) {
                item { Text("No products yet.", modifier = Modifier.padding(16.dp)) }
            }
        }
    }
}
