package org.sacada.composegenerator

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import java.io.File

/** Utility to extract template composable source from a Kotlin file. */
object TemplateExtractor {
    data class TemplateInfo(
        val imports: List<String>,
        val body: String,
    )

    fun extractTemplate(
        file: File,
        functionName: String,
    ): TemplateInfo {
        setIdeaIoUseFallback()
        val configuration =
            CompilerConfiguration().apply {
                put(
                    CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
                    org.jetbrains.kotlin.cli.common.messages.MessageCollector.NONE,
                )
            }
        val disposable = Disposer.newDisposable()
        return try {
            val environment =
                KotlinCoreEnvironment.createForProduction(
                    disposable,
                    configuration,
                    EnvironmentConfigFiles.JVM_CONFIG_FILES,
                )
            val project = environment.project
            val psiFactory = KtPsiFactory(project, false)
            val ktFile = psiFactory.createFile(file.name, file.readText())
            val imports = ktFile.importDirectives.mapNotNull { it.importPath?.pathStr }
            val function = findFunctionRecursively(ktFile, functionName)
                ?: error("Function $functionName not found in ${file.path}")
            val body = function.bodyExpression?.text ?: ""
            TemplateInfo(imports, body.trim())
        } finally {
            Disposer.dispose(disposable)
        }
    }

    private fun findFunctionRecursively(
        element: KtElement,
        name: String,
    ): KtNamedFunction? {
        return element.collectDescendantsOfType<KtNamedFunction> { it.name == name }.firstOrNull()
    }
}
