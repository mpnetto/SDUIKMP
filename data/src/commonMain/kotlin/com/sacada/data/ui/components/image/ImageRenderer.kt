package com.sacada.data.ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// import coil.compose.rememberAsyncImagePainter
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component

@RegisterComponent
object ImageRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
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
