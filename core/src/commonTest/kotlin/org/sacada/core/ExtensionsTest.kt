import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.core.model.ViewComponent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.sacada.core.util.getStringAttribute
import org.sacada.core.util.getBooleanAttribute
import org.sacada.core.util.getSubAttributes
import org.sacada.core.util.isValid

class ExtensionsTest {
    private val component = ViewComponent(
        id = "c1",
        type = "TextField",
        attributes = buildJsonObject {
            put("text", JsonPrimitive("hello"))
            put("enabled", JsonPrimitive(true))
            put("validation", buildJsonObject {
                put("required", JsonPrimitive(true))
                put("minLength", JsonPrimitive(3))
                put("regex", JsonPrimitive("\\d+"))
            })
        }
    )

    @Test
    fun testGetStringAttribute() {
        assertEquals("hello", component.getStringAttribute("text"))
        assertEquals("", component.getStringAttribute("missing"))
    }

    @Test
    fun testGetBooleanAttribute() {
        assertTrue(component.getBooleanAttribute("enabled"))
        assertFalse(component.getBooleanAttribute("other"))
    }

    @Test
    fun testGetSubAttributes() {
        val sub = component.getSubAttributes("validation")
        assertEquals("true", sub?.get("required")?.jsonPrimitive?.content)
    }

    @Test
    fun testIsValid() {
        assertFalse(component.isValid(""))
        assertFalse(component.isValid("ab"))
        assertFalse(component.isValid("abc"))
        assertTrue(component.isValid("123"))
    }
}
