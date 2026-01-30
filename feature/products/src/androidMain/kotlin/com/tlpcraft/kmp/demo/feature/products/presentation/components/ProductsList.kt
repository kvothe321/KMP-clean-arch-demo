package com.tlpcraft.kmp.demo.feature.products.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlpcraft.kmp.demo.domain.model.ProductPreview

@Composable
fun ProductsList(
    products: List<ProductPreview>,
    hasMorePages: Boolean,
    isLoadingMore: Boolean,
    onProductClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(products, key = { it.id }) { product ->
            Card(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = { onProductClick(product.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(product.title, style = MaterialTheme.typography.titleMedium)
                    Text(product.description, style = MaterialTheme.typography.bodySmall)
                    Text("Category: ${product.category}", style = MaterialTheme.typography.bodySmall)
                    Text("Price: \$${product.price}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        if (hasMorePages) {
            item {
                if (isLoadingMore) {
                    Text("Loading more...", modifier = Modifier.padding(16.dp))
                } else {
                    Button(
                        onClick = onLoadMore,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Load More")
                    }
                }
            }
        }

        item {
            Button(
                onClick = onRefresh,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Refresh")
            }
        }
    }
}
