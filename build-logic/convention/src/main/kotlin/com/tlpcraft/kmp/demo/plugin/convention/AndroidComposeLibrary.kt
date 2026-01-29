package com.tlpcraft.kmp.demo.plugin.convention

import com.tlpcraft.kmp.demo.plugin.shared.PluginDefinitions.COMPOSE_COMPILER
import com.tlpcraft.kmp.demo.plugin.shared.PluginDefinitions.COMPOSE_MULTIPLATFORM
import com.tlpcraft.kmp.demo.plugin.shared.applyPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidComposeLibrary : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                COMPOSE_MULTIPLATFORM,
                COMPOSE_COMPILER
            )
            declareDefaultDependencies()
        }
    }

    private fun Project.declareDefaultDependencies() {
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            // TODO: simplify the dependency mess on each feature module
        }
    }
}
