package com.tlpcraft.kmp.demo.feature.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tlpcraft.kmp.demo.feature.favorites.presentation.state.ScreenUiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen() {
    val viewModel = koinViewModel<FavoritesViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is ScreenUiState.Loading -> {
                Text("Loading...")
            }

            is ScreenUiState.Empty -> {
                Text("No favorite products yet")
            }

            is ScreenUiState.Error -> {
                Text("Error: ${state.message}")
            }

            is ScreenUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.products) { product ->
                        Card(modifier = Modifier.fillMaxWidth(0.8f)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(product.title, style = MaterialTheme.typography.titleMedium)
                                Text(product.description, style = MaterialTheme.typography.bodySmall)
                                Text("Category: ${product.category}", style = MaterialTheme.typography.bodySmall)
                                Text("Price: \$${product.price}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
