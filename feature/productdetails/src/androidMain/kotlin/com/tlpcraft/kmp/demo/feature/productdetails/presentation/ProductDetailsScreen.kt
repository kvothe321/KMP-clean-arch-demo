package com.tlpcraft.kmp.demo.feature.productdetails.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tlpcraft.kmp.demo.domain.model.Product
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.components.ProductInfoRow
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.state.ProductDetailsUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailsScreen(productId: Int) {
    val viewModel: ProductDetailsViewModel = koinViewModel { parametersOf(productId) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.setProductId(productId)
    }

    Scaffold(
        floatingActionButton = {
            (uiState as? ProductDetailsUiState.Success)?.let {
                FloatingActionButton(onClick = viewModel::toggleFavorite) {
                    Icon(
                        imageVector = if (it.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite"
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ProductDetailsUiState.Loading -> CircularProgressIndicator()
                is ProductDetailsUiState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
                is ProductDetailsUiState.Success -> ProductDetailsContent(state.product)
            }
        }
    }
}

@Composable
private fun ProductDetailsContent(product: Product) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(product.images) { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = product.title,
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        item {
            Text(product.title, style = MaterialTheme.typography.headlineMedium)
            Text(product.category, style = MaterialTheme.typography.titleSmall, color = Color.Gray)
        }

        item {
            Text(product.description, style = MaterialTheme.typography.bodyMedium)
        }

        item {
            HorizontalDivider()
        }

        item {
            ProductInfoRow("Price", "$${product.price} (-${product.discountPercentage}%)")
            ProductInfoRow("Rating", "${product.rating} / 5.0")
            ProductInfoRow("Stock", product.stock.toString())
            ProductInfoRow("Brand", product.brand ?: "N/A")
            ProductInfoRow("SKU", product.sku)
        }

        item {
            HorizontalDivider()
        }

        item {
            Text("Shipping & Warranty", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            ProductInfoRow("Warranty", product.warrantyInformation)
            ProductInfoRow("Shipping", product.shippingInformation)
            ProductInfoRow("Return Policy", product.returnPolicy)
        }
    }
}
