import com.tlpcraft.kmp.demo.plugin.config.AndroidBuildConfig.ModuleNamespace.FEATURE_NAMESPACE


plugins {
    id(libs.plugins.tlpcraft.kotlin.multiplatform.library.get().pluginId)
}

android {
    namespace = "${FEATURE_NAMESPACE}.productdetails"
}
