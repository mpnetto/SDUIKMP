package org.sacada.figma2sdui.data.nodes

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
            instance.resolveComponents()?.forEach { component ->
                findComponentId(component)?.let { return it }
            }
            return instance.resolveComponentId()
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
