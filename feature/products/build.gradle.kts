import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig.ModuleNamespace.FEATURE_NAMESPACE


plugins {
    id(libs.plugins.tlpcraft.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.tlpcraft.android.compose.library.get().pluginId)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(projects.shared.core.domain)
            implementation(projects.shared.core.application)
            implementation(projects.shared.data)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "${FEATURE_NAMESPACE}.products"
}
