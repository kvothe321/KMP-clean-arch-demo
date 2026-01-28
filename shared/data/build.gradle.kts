import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig.ModuleNamespace.DATA_NAMESPACE


plugins {
    id(libs.plugins.tlpcraft.kotlin.multiplatform.library.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.domain)
        }
    }
}

android {
    namespace = DATA_NAMESPACE
}
