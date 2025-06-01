package org.sacada.figma2sdui.data

import org.sacada.figma2sdui.data.nodes.BooleanOperation
import org.sacada.figma2sdui.data.nodes.Component
import org.sacada.figma2sdui.data.nodes.Document
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.Line
import org.sacada.figma2sdui.data.nodes.Page
import org.sacada.figma2sdui.data.nodes.RectangleNode
import org.sacada.figma2sdui.data.nodes.RootDocument
import org.sacada.figma2sdui.data.nodes.Text
import org.sacada.figma2sdui.data.nodes.Vector

interface Visitor<T> {
    fun visit(
        rootDocument: RootDocument,
        additionalData: Any? = null,
    ): T

    fun visit(
        document: Document,
        additionalData: Any? = null,
    ): T

    fun visit(
        page: Page,
        additionalData: Any? = null,
    ): T

    fun visit(
        rectangleNode: RectangleNode,
        additionalData: Any? = null,
    ): T

    fun visit(
        frame: Frame,
        additionalData: Any? = null,
    ): T

    fun visit(
        instance: Instance,
        additionalData: Any? = null,
    ): T

    fun visit(
        component: Component,
        additionalData: Any? = null,
    ): T

    fun visit(
        vector: Vector,
        additionalData: Any? = null,
    ): T

    fun visit(
        booleanOperation: BooleanOperation,
        additionalData: Any? = null,
    ): T

    fun visit(
        line: Line,
        additionalData: Any? = null,
    ): T

    fun visit(
        text: Text,
        additionalData: Any? = null,
    ): T
}
