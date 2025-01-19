package com.sacada.figma2sdui.data.nodes

import kotlin.reflect.KCallable
import kotlin.reflect.KClass

class NodeHelper {
    companion object {
        private val reflectionCache = mutableMapOf<KClass<*>, ReflectionInfo>()

        private data class ReflectionInfo(
            val componentIdProperty: KCallable<*>?,
            val componentsProperty: KCallable<*>?
        )

        fun findComponentId(instance: BaseComponent): String? {
            return try {
//                val info = getReflectionInfo(instance::class)
//
//                val components = info.componentsProperty?.call(instance) as? Array<*>
//                components?.forEach { component ->
//                    (component as? BaseComponent)?.let { baseComponent ->
//                        findComponentId(baseComponent)?.let { return it }
//                    }
//                }
//
//                val componentId = info.componentIdProperty?.call(instance) as? String
//                if (componentId != null) return componentId

                null
            } catch (e: Exception) {
                null
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
