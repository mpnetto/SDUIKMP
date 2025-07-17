import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.sacada.core.util.JsonParser

class JsonParserTest {
    @Test
    fun parseValidJson() {
        val json = """
            {"screens":[{"id":"home","type":"screen","layout":{"id":"layout","type":"column"}}]}
        """.trimIndent()
        val result = JsonParser.parseScreens(json)
        assertNotNull(result)
        assertEquals(1, result.screens.size)
        assertEquals("home", result.screens[0].id)
    }

    @Test
    fun parseInvalidJsonReturnsNull() {
        val invalidJson = "{invalid}"
        val result = JsonParser.parseScreens(invalidJson)
        assertNull(result)
    }
}
