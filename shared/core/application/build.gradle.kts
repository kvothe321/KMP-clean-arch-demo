import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig.ModuleNamespace.APPLICATION_NAMESPACE


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
    namespace = APPLICATION_NAMESPACE
}
