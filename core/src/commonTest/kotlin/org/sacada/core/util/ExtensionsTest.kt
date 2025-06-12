import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.core.model.ViewComponent
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExtensionsTest {
    private fun componentWithValidation() = ViewComponent(
        id = "textField",
        type = "TextField",
        attributes = buildJsonObject {
            put("validation", buildJsonObject {
                put("required", JsonPrimitive(true))
                put("minLength", JsonPrimitive(3))
                put("regex", JsonPrimitive("[a-z]+"))
            })
        }
    )

    @Test
    fun isValid_validValue_returnsTrue() {
        val component = componentWithValidation()
        assertTrue(component.isValid("abc"))
    }

    @Test
    fun isValid_invalidValue_returnsFalse() {
        val component = componentWithValidation()
        assertFalse(component.isValid("ab"))
        assertFalse(component.isValid("123"))
        assertFalse(component.isValid(""))
    }
}
