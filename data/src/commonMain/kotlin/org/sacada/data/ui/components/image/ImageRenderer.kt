package org.sacada.data.ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// import coil.compose.rememberAsyncImagePainter
import org.sacada.annotation.RegisterComponent
import org.sacada.core.blueprint.Blueprint
import org.sacada.core.blueprint.GenericBlueprint
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component

@RegisterComponent
object ImageRenderer : Component.Renderer {
    @Composable
    override fun Render(
        blueprint: Blueprint,
        modifier: Modifier?,
    ) {
        val component = (blueprint as? GenericBlueprint)?.component ?: return
        val imageUrl = component.getStringAttribute("imageUrl")
        val contentDescription = component.getStringAttribute("contentDescription")

//        if (imageUrl.isNotEmpty()) {
//            Image(
//                painter = rememberAsyncImagePainter(model = imageUrl),
//                contentDescription = contentDescription,
//                contentScale = ContentScale.Crop
//            )
//        }
    }
}
