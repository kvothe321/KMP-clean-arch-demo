import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig

plugins {
    id(libs.plugins.tlpcraft.kotlin.multiplatform.library.get().pluginId)
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = AndroidBuildConfig.ModuleNamespace.SHARED_NAMESPACE
}
