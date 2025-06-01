package org.sacada.jsonbuilder.generator

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.sacada.data.ui.components.ComponentRegistry
import org.sacada.figma2sdui.analyser.ComponentType
import org.sacada.figma2sdui.data.Visitor
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
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

class JsonBuilderVisitor : Visitor<JsonObject> {
    private lateinit var componentDescriptions: Map<String, RootComponentDescription>
    private val screens = mutableListOf<JsonObject>()

    override fun visit(
        rootDocument: RootDocument,
        additionalData: Any?,
    ): JsonObject {
        this.componentDescriptions = rootDocument.componentDescriptions

        rootDocument.document.accept(this)

        return buildJsonObject {
            put(
                "screens",
                buildJsonArray {
                    screens.forEach { screenJson ->
                        add(screenJson)
                    }
                },
            )
        }
    }

    override fun visit(
        document: Document,
        additionalData: Any?,
    ): JsonObject {
        document.pages.forEach { page ->
            page.accept(this)
        }

        return buildJsonObject {}
    }

    override fun visit(
        page: Page,
        additionalData: Any?,
    ): JsonObject {
        page.frames.forEach { frame ->
            val frameJson = frame.accept(this)
            if (!frameJson.isEmpty()) {
                screens.add(frameJson)
            }
        }
        return buildJsonObject {}
    }

    override fun visit(
        rectangleNode: RectangleNode,
        additionalData: Any?,
    ): JsonObject = buildJsonObject {}

    override fun visit(
        frame: Frame,
        additionalData: Any?,
    ): JsonObject {
        if (frame.componentType == ComponentType.SCREEN_FRAME) {
            return buildJsonObject {
                put("id", JsonPrimitive(frame.id))
                put("name", JsonPrimitive(frame.name))
                put("type", JsonPrimitive("ScreenFrame"))

                val attributesJson =
                    buildJsonObject {
                        put("horizontalAlignment", JsonPrimitive("Start"))
                        put("paddingLeft", JsonPrimitive(frame.paddingLeft))
                        put("paddingRight", JsonPrimitive(frame.paddingRight))
                        put("paddingTop", JsonPrimitive(frame.paddingTop))
                        put("paddingBottom", JsonPrimitive(frame.paddingBottom))
                        put("itemSpacing", JsonPrimitive(frame.itemSpacing))
                        put("verticalAlignment", JsonPrimitive(frame.constraints?.vertical?.name))
                        put(
                            "horizontalAlignment",
                            JsonPrimitive(frame.constraints?.horizontal?.name),
                        )
                    }
                put("attributes", attributesJson)

                var topBarElement: JsonObject? = null
                var bottomBarElement: JsonObject? = null
                var layoutElement: JsonObject? = null

                frame.components.forEach { component ->
                    val componentJson = component.accept(this@JsonBuilderVisitor)

                    val typeString = (componentJson["type"] as? JsonPrimitive)?.content

                    if (typeString?.contains("TopBar") == true) {
                        topBarElement =
                            buildJsonObject {
                                componentJson.forEach { (key, value) ->
                                    put(key, value)
                                }
                            }
                    } else if (typeString?.contains("BottomBar") == true) {
                        bottomBarElement =
                            buildJsonObject {
                                componentJson.forEach { (key, value) ->
                                    put(key, value)
                                }
                            }
                    } else if (typeString?.contains("Column") == true) {
                        val attributes =
                            (componentJson["attributes"] as? JsonObject) ?: buildJsonObject {}
                        val updatedAttributes =
                            buildJsonObject {
                                attributes.forEach { (k, v) ->
                                    put(k, v)
                                }
                                put("verticalArrangement", JsonPrimitive("SpaceBetween"))
                                put("fillMaxSize", JsonPrimitive("true"))
                            }
                        layoutElement =
                            buildJsonObject {
                                componentJson.forEach { (key, value) ->
                                    if (key == "attributes") {
                                        put("attributes", updatedAttributes)
                                    } else {
                                        put(key, value)
                                    }
                                }
                            }
                    } else if (typeString?.contains("Row") == true) {
                        val attributes =
                            (componentJson["attributes"] as? JsonObject) ?: buildJsonObject {}
                        val updatedAttributes =
                            buildJsonObject {
                                attributes.forEach { (k, v) ->
                                    put(k, v)
                                }
                                put("horizontalArrangement", JsonPrimitive("SpaceBetween"))
                                put("fillMaxSize", JsonPrimitive("true"))
                            }
                        layoutElement =
                            buildJsonObject {
                                componentJson.forEach { (key, value) ->
                                    if (key == "attributes") {
                                        put("attributes", updatedAttributes)
                                    } else {
                                        put(key, value)
                                    }
                                }
                            }
                    }
                }

                topBarElement?.let {
                    put("topBar", it)
                }

                bottomBarElement?.let {
                    put("bottomBar", it)
                }

                layoutElement?.let {
                    put("layout", it)
                }
            }
        } else if (frame.componentType == ComponentType.COLUMN ||
            frame.componentType == ComponentType.ROW
        ) {
            val generator = frame.componentType.name.let { ComponentRegistry.getGenerator(it) }

            val json =
                generator.generateJson(frame) { mutableMap ->

                    val childrenArray =
                        buildJsonArray {
                            frame.components.forEach { child ->
                                val childJson = child.accept(this@JsonBuilderVisitor)
                                if (!childJson.isEmpty()) {
                                    add(childJson)
                                }
                            }
                        }

                    mutableMap["children"] = childrenArray

                    JsonObject(mutableMap)
                }

            return json
        } else {
            val childrenJsonList = mutableListOf<JsonObject>()
            frame.components.forEach { component ->
                val componentJson = component.accept(this)
                if (!componentJson.isEmpty()) {
                    childrenJsonList.add(componentJson)
                }
            }
            if (childrenJsonList.isNotEmpty()) {
                return childrenJsonList.first()
            }
        }

        return buildJsonObject {}
    }

    override fun visit(
        instance: Instance,
        additionalData: Any?,
    ): JsonObject {
        val generator = instance.componentType.name.let { ComponentRegistry.getGenerator(it) }

        val json =
            generator.generateJson(instance, componentDescriptions) { mutableMap ->

                val childrenArray =
                    buildJsonArray {
                        instance.components.forEach { child ->
                            val childJson = child.accept(this@JsonBuilderVisitor)
                            add(childJson)
                        }
                    }

                mutableMap["children"] = childrenArray

                JsonObject(mutableMap)
            }

        return json
    }

    override fun visit(
        component: Component,
        additionalData: Any?,
    ): JsonObject = buildJsonObject {}

    override fun visit(
        vector: Vector,
        additionalData: Any?,
    ): JsonObject = buildJsonObject {}

    override fun visit(
        booleanOperation: BooleanOperation,
        additionalData: Any?,
    ): JsonObject = buildJsonObject {}

    override fun visit(
        line: Line,
        additionalData: Any?,
    ): JsonObject = buildJsonObject {}

    override fun visit(
        text: Text,
        additionalData: Any?,
    ): JsonObject =
        buildJsonObject {
            put("id", JsonPrimitive(text.id))
            put("type", JsonPrimitive("Text"))
            put(
                "attributes",
                buildJsonObject {
                    put("content", JsonPrimitive(text.characters))

                    put(
                        "style",
                        buildJsonObject {
                            put("fontFamily", JsonPrimitive(text.style.fontFamily))
                            put("fontPostScriptName", JsonPrimitive(text.style.fontPostScriptName))
                            put("paragraphSpacing", JsonPrimitive(text.style.paragraphSpacing))
                            put("paragraphIndent", JsonPrimitive(text.style.paragraphIndent))
                            put("listSpacing", JsonPrimitive(text.style.listSpacing))
                            put("italic", JsonPrimitive(text.style.italic))
                            put("fontWeight", JsonPrimitive(text.style.fontWeight))
                            put("fontSize", JsonPrimitive(text.style.fontSize))
                            put(
                                "textAlignHorizontal",
                                JsonPrimitive(text.style.textAlignHorizontal.name),
                            )
                            put(
                                "textAlignVertical",
                                JsonPrimitive(text.style.textAlignVertical.name),
                            )
                            put("letterSpacing", JsonPrimitive(text.style.letterSpacing))
                            put("lineHeightPx", JsonPrimitive(text.style.lineHeightPx))
                            put("lineHeightPercent", JsonPrimitive(text.style.lineHeightPercent))
                            put(
                                "lineHeightPercentFontSize",
                                JsonPrimitive(text.style.lineHeightPercentFontSize),
                            )
                            put("lineHeightUnit", JsonPrimitive(text.style.lineHeightUnit))
                        },
                    )
                },
            )
        }
}
