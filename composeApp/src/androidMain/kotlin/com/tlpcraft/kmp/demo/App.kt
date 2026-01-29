package com.tlpcraft.kmp.demo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.tlpcraft.kmp.demo.feature.favorites.presentation.FavoritesScreen
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.ProductDetailsScreen
import com.tlpcraft.kmp.demo.feature.products.presentation.ProductsScreen
import com.tlpcraft.kmp.demo.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun App() {
    MaterialTheme {
        val backStack = rememberNavBackStack(Destination.Products)
        val currentScreen = backStack.lastOrNull()
        val currentKey = backStack.last()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentKey.toString()) },
                    navigationIcon = {
                        if (currentScreen is Destination.ProductDetails) {
                            IconButton(onClick = { backStack.removeLast() }) {
                                Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen is Destination.Products,
                        onClick = {
                            if (currentScreen !is Destination.Products) {
                                backStack.clear()
                                backStack.add(Destination.Products)
                            }
                        },
                        icon = { Icon(Icons.Default.Home, "Products") }
                    )
                    NavigationBarItem(
                        selected = currentScreen is Destination.Favorites,
                        onClick = {
                            if (currentScreen !is Destination.Favorites) {
                                backStack.clear()
                                backStack.add(Destination.Favorites)
                            }
                        },
                        icon = { Icon(Icons.Filled.FavoriteBorder, "Favorites") }
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<Destination.Products> {
                            ProductsScreen(onProductClick = { productId ->
                                backStack.add(Destination.ProductDetails(productId))
                            })
                        }
                        entry<Destination.ProductDetails> { ProductDetailsScreen() }
                        entry<Destination.Favorites> { FavoritesScreen() }
                    }
                )
            }
        }
    }
}
