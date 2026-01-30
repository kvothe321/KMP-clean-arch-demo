plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.touchlab.skie) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.mokkery) apply false

    alias(libs.plugins.tlpcraft.kotlin.multiplatform.library) apply false
    alias(libs.plugins.tlpcraft.android.compose.library) apply false
}
