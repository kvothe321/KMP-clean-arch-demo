package com.tlpcraft.kmp.demo.feature.products.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyContent(isSearchActive: Boolean, searchQuery: String, onClearSearch: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isSearchActive) {
                "No products found for \"$searchQuery\""
            } else {
                "No products available"
            },
            style = MaterialTheme.typography.bodyLarge
        )
        if (isSearchActive) {
            Button(
                onClick = onClearSearch,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Clear Search")
            }
        }
    }
}
