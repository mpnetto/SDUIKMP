package org.sacada.figma2sdui.analyser

import org.sacada.figma2sdui.data.AdditionalData
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.BooleanOperation
import org.sacada.figma2sdui.data.nodes.Component
import org.sacada.figma2sdui.data.nodes.Document
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.Line
import org.sacada.figma2sdui.data.nodes.NodeType
import org.sacada.figma2sdui.data.nodes.Page
import org.sacada.figma2sdui.data.nodes.RectangleNode
import org.sacada.figma2sdui.data.nodes.RootDocument
import org.sacada.figma2sdui.data.nodes.Text
import org.sacada.figma2sdui.data.nodes.Vector
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

class AnalyserVisitor : Visitor<AnalyserResult> {
    private val errorMessages = mutableListOf<String>()
    private val warningsMessages = mutableListOf<String>()
    private lateinit var componentDescriptions: Map<String, RootComponentDescription>

    private fun sanitizeString(str: String): String = str.replace("/", "")

    /**
     * Semantic check between parent type and children types.
     * Returns False if there are errors
     */
    private fun checkSemantics(
        parentType: ComponentType,
        childrenTypes: List<ComponentType>,
    ): Boolean {
        // TODO: check semantics for various types and their children
        return true
    }

    private fun checkM3Semantics(parentType: ComponentType): Boolean {
        // TODO: check semantics for various M3 components
        return true
    }

    private fun tagIsUnknown(
        type: ComponentType,
        componentName: String,
    ): Boolean {
        if (type == ComponentType.UNKNOWN) {
            val errorString = "Component $componentName has invalid tag"
            errorMessages.add(errorString)
            return true
        }

        return false
    }

    override fun visit(
        rootDocument: RootDocument,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        this.componentDescriptions = rootDocument.componentDescriptions
        rootDocument.document.accept(this, null)

        return AnalyserResult(
            errorMessages = errorMessages,
            warningsMessages = warningsMessages,
        )
    }

    override fun visit(
        document: Document,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        document.pages.forEach { page ->
            page.accept(this, null)
        }

        return AnalyserResult()
    }

    override fun visit(
        page: Page,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        page.frames.forEach { frame ->
            frame.accept(this, AdditionalData(page, page.type))
        }

        return AnalyserResult()
    }

    override fun visit(
        rectangleNode: RectangleNode,
        additionalData: AdditionalData?,
    ): AnalyserResult = AnalyserResult()

    override fun visit(
        frame: Frame,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        if (additionalData != null && additionalData.parentType == NodeType.CANVAS) {
            frame.componentType = ComponentType.SCREEN_FRAME
        } else {
            val componentType = ComponentType.findTaggedComponentType(frame.name)
            if (tagIsUnknown(componentType, frame.name)) {
                return AnalyserResult(ComponentType.UNKNOWN)
            }

            frame.componentType =
                if (componentType == ComponentType.UNTAGGED) {
                    ComponentType.COMPONENT_FRAME
                } else {
                    componentType
                }
        }

        val childrenAnalyserResults =
            frame.components.map { component ->
                component.accept(this, null)
            }
        checkSemantics(
            frame.componentType,
            childrenAnalyserResults.map { result -> result.componentType },
        )

        return AnalyserResult()
    }

    override fun visit(
        instance: Instance,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        val componentType = ComponentType.findTaggedComponentType(instance.name)
        if (tagIsUnknown(componentType, instance.name)) {
            return AnalyserResult(ComponentType.UNKNOWN)
        }

        if (componentType == ComponentType.LIST) {
            componentType.additionalData = ComponentType.findListDensity(instance.name)
        }

        instance.componentType = componentType
        instance.name = sanitizeString(instance.name)
        if (instance.componentType.isM3Tag) {
            checkM3Semantics(instance.componentType)
            // return here probably
        }

        val childrenAnalyserResults =
            instance.components.map { component ->
                component.accept(this, null)
            }
        checkSemantics(
            instance.componentType,
            childrenAnalyserResults.map { result -> result.componentType },
        )

        return AnalyserResult()
    }

    override fun visit(
        component: Component,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        val componentType = ComponentType.findTaggedComponentType(component.name)
        if (tagIsUnknown(componentType, component.name)) {
            return AnalyserResult(ComponentType.UNKNOWN)
        }

        if (componentType == ComponentType.LIST) {
            componentType.additionalData = ComponentType.findListDensity(component.name)
        }

        component.componentType = componentType

        val childComponentResults =
            component.components.map { childComponent ->
                childComponent.accept(this, null)
            }
        checkSemantics(
            component.componentType,
            childComponentResults.map { result -> result.componentType },
        )

        return AnalyserResult(component.componentType)
    }

    override fun visit(
        vector: Vector,
        additionalData: AdditionalData?,
    ): AnalyserResult = AnalyserResult()

    override fun visit(
        booleanOperation: BooleanOperation,
        additionalData: AdditionalData?,
    ): AnalyserResult = AnalyserResult()

    override fun visit(
        line: Line,
        additionalData: AdditionalData?,
    ): AnalyserResult = AnalyserResult()

    override fun visit(
        text: Text,
        additionalData: AdditionalData?,
    ): AnalyserResult {
        text.componentType = ComponentType.TEXT
        return AnalyserResult(text.componentType)
    }
}
