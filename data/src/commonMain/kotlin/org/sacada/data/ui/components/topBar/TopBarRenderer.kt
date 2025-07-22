package org.sacada.data.ui.components.topBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.annotation.RegisterComponent
import org.sacada.core.blueprint.Blueprint
import org.sacada.core.blueprint.TopBarBlueprint
import org.sacada.core.model.ViewComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.box.BoxRenderer

@RegisterComponent
object TopBarRenderer : Component.Renderer {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Render(
        blueprint: Blueprint,
        modifier: Modifier?,
    ) {
        blueprint as TopBarBlueprint
        val scrollBehavior = resolveScrollBehavior(blueprint.scrollBehavior)
        val appBar = remember { resolveAppBarType(blueprint.variant) }

        val title = createTitleComposable(blueprint.title)
        val navigationIcon = createNavigationIconComposable(blueprint.navigationIcon)
        val actions = createActionsFromChildren(blueprint.actions)

        val paddingLeft = blueprint.paddingLeft
        val paddingRight = blueprint.paddingRight
        val paddingTop = blueprint.paddingTop
        val paddingBottom = blueprint.paddingBottom

        val paddingModifier =
            Modifier.padding(
                start = paddingLeft.dp,
                end = paddingRight.dp,
                top = paddingTop.dp,
                bottom = paddingBottom.dp,
            )

        val colors =
            topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )

        appBar(
            title,
            navigationIcon,
            actions,
            colors,
            scrollBehavior,
            modifier?.then(paddingModifier) ?: paddingModifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun resolveScrollBehavior(scrollBehaviorType: String?): TopAppBarScrollBehavior? {
    val appBarState = rememberTopAppBarState()
    return when (scrollBehaviorType) {
        "enterAlways" -> TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
        "exitUntilCollapsed" -> TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
        "pinned" -> TopAppBarDefaults.pinnedScrollBehavior(appBarState)
        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun resolveAppBarType(
    type: String,
): @Composable (
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    colors: TopAppBarColors,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier,
) -> Unit =
    when (type) {
        "large" -> { title, navigationIcon, actions, colors, scrollBehavior, modifier ->
            LargeTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
            )
        }

        "medium" -> { title, navigationIcon, actions, colors, scrollBehavior, modifier ->
            MediumTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
            )
        }

        "center", "Small centered" -> {
            title,
            navigationIcon,
            actions,
            colors,
            scrollBehavior,
            modifier,
            ->
            CenterAlignedTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
            )
        }

        else -> { title, navigationIcon, actions, colors, scrollBehavior, modifier ->
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
            )
        }
    }

@Composable
private fun createTitleComposable(barTitle: String): @Composable () -> Unit =
    {
        Text(
            text = barTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

@Composable
private fun createNavigationIconComposable(component: ViewComponent): @Composable () -> Unit =
    {
        component.let {
            BoxRenderer.Render(it)
        }
    }

@Composable
private fun createActionsFromChildren(children: List<ViewComponent>): @Composable RowScope.() -> Unit =
    {
        children.forEach { actionComponent ->
            BoxRenderer.Render(actionComponent)
        }
    }

@Preview()
@Composable
fun PreviewRenderTopBar() {
    val sampleComponent =
        ViewComponent(
            type = "center",
            attributes =
                mapOf(
                    "title" to JsonPrimitive("Sample Top Bar"),
                    "scrollBehavior" to JsonPrimitive("enterAlways"),
                ),
            children =
                listOf(
                    ViewComponent(
                        type = "navigationIcon",
                        attributes =
                            mapOf(
                                "iconName" to JsonPrimitive("arrow_back"),
                            ),
                    ),
                    ViewComponent(
                        type = "Action",
                        attributes =
                            mapOf(
                                "iconName" to JsonPrimitive("menu"),
                            ),
                    ),
                ),
        )
    TopBarRenderer.Render(TopBarBlueprint.from(sampleComponent))
}
