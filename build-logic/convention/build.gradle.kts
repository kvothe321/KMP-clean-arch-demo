plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.multiplatform.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

group = "com.tlpcraft.kmp.demo.plugin.convention"

gradlePlugin {
    plugins {
        register("kotlinMultiplatformLibrary") {
            id = "${project.group}.kotlin.multiplatform.library"
            implementationClass = "${project.group}.KotlinMultiplatformLibrary"
        }
    }
}
