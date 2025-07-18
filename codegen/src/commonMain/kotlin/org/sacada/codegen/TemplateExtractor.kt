package org.sacada.codegen

import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import java.io.File

object TemplateExtractor {
    data class Template(val imports: List<String>, val body: String)

    fun extractTemplate(file: File, functionName: String): Template? {
        setIdeaIoUseFallback()
        val disposable: Disposable = Disposer.newDisposable()
        try {
            val configuration = CompilerConfiguration()
            val environment = KotlinCoreEnvironment.createForProduction(
                disposable,
                configuration,
                EnvironmentConfigFiles.JVM_CONFIG_FILES
            )
            val psiFactory = KtPsiFactory(environment.project, markGenerated = false)
            val ktFile = psiFactory.createFile(file.name, file.readText())

            val function = ktFile.declarations
                .filterIsInstance<KtNamedFunction>()
                .find { it.name == functionName }
                ?: return null

            val imports = ktFile.importList?.imports?.map { it.text } ?: emptyList()
            val body = function.bodyExpression?.text ?: return null
            return Template(imports, body)
        } finally {
            Disposer.dispose(disposable)
        }
    }
}
