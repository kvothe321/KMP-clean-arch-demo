package com.tlpcraft.kmp.demo.feature.productdetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.state.ScreenUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailsScreen(productId: Int) {
    val viewModel = koinViewModel<ProductDetailsViewModel> { parametersOf(productId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                        Text(description, style = MaterialTheme.typography.bodyLarge)
                        Text("Category: $category")
                        Text("Price: $price")
                    }
                }
            }
        }
    }
}
