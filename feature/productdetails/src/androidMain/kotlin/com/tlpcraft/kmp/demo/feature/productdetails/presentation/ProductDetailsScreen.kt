package com.tlpcraft.kmp.demo.feature.productdetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.state.ScreenUiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(productId: Int) {
    val viewModel = koinViewModel<ProductDetailsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is ScreenUiState.Error -> {
                Text("Error: ${state.message}")
            }

            ScreenUiState.Loading -> {
                Text("Loading...")
            }

            is ScreenUiState.Success -> {
                state.product.apply {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(title, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(description, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Category: $category")
                        Text("Price: \$$price")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (state.isFavorite) "Remove from favorites" else "Add to favorites"
                            )
                            Text(if (state.isFavorite) " Remove from Favorites" else " Add to Favorites")
                        }
                    }
                }
            }
        }
    }
}
