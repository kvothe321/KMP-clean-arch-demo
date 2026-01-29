import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig.ModuleNamespace.DOMAIN_NAMESPACE


plugins {
    id(libs.plugins.tlpcraft.kotlin.multiplatform.library.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    namespace = DOMAIN_NAMESPACE
}
