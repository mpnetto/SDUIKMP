package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

class NodeHelper {
    companion object {
        private val reflectionCache = mutableMapOf<KClass<*>, ReflectionInfo>()

        private data class ReflectionInfo(
            val componentIdProperty: KCallable<*>?,
            val componentsProperty: KCallable<*>?,
        )

        fun findComponentId(instance: BaseComponent): String? {
            val properties = (instance as? Instance)?.componentProperties ?: return null

            return properties.firstNotNullOfOrNull { (key, value) ->
                if (key.contains("Icon")) {
                    (value.value as JsonPrimitive).contentOrNull
                } else {
                    null
                }
            }
        }

//        private fun getReflectionInfo(kClass: KClass<*>): ReflectionInfo =
//            reflectionCache.getOrPut(kClass) {
//                val members = kClass.members
//                val componentIdProp = members.firstOrNull { it.name == "componentId" }
//                val componentsProp = members.firstOrNull { it.name == "components" }
//                ReflectionInfo(componentIdProp, componentsProp)
//            }
    }
}
