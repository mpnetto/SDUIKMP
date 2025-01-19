package com.sacada.figma2sdui.data

import com.sacada.figma2sdui.data.nodes.BooleanOperation
import com.sacada.figma2sdui.data.nodes.Component
import com.sacada.figma2sdui.data.nodes.Document
import com.sacada.figma2sdui.data.nodes.Frame
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.Line
import com.sacada.figma2sdui.data.nodes.Page
import com.sacada.figma2sdui.data.nodes.RectangleNode
import com.sacada.figma2sdui.data.nodes.RootDocument
import com.sacada.figma2sdui.data.nodes.Text
import com.sacada.figma2sdui.data.nodes.Vector

interface Visitor<T> {
    fun visit(rootDocument: RootDocument, additionalData: AdditionalData? = null): T

    fun visit(document: Document, additionalData: AdditionalData? = null): T

    fun visit(page: Page, additionalData: AdditionalData? = null): T

    fun visit(rectangleNode: RectangleNode, additionalData: AdditionalData? = null): T

    fun visit(frame: Frame, additionalData: AdditionalData? = null): T

    fun visit(instance: Instance, additionalData: AdditionalData? = null): T

    fun visit(component: Component, additionalData: AdditionalData? = null): T

    fun visit(vector: Vector, additionalData: AdditionalData? = null): T

    fun visit(booleanOperation: BooleanOperation, additionalData: AdditionalData? = null): T

    fun visit(line: Line, additionalData: AdditionalData? = null): T

    fun visit(text: Text, additionalData: AdditionalData? = null): T
}
