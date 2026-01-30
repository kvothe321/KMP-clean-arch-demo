import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig.ModuleNamespace.APPLICATION_NAMESPACE


plugins {
    id(libs.plugins.tlpcraft.kotlin.multiplatform.library.get().pluginId)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kover)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.domain)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.testJunit)
            implementation(libs.koin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = APPLICATION_NAMESPACE
}
