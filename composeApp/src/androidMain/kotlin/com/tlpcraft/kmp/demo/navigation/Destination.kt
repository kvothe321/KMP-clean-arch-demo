package com.tlpcraft.kmp.demo.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination : NavKey {

    @Serializable
    data object Products : Destination

    @Serializable
    data class ProductDetails(val id: Int) : Destination

    @Serializable
    data object Favorites : Destination
}
