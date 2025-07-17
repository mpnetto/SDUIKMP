import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JsonParserTest {
    @Test
    fun parseScreens_validJson_returnsScreens() {
        val json = """
            {"screens": [{"id": "screen1"}]}
        """.trimIndent()
        val result = JsonParser.parseScreens(json)
        assertNotNull(result)
        assertEquals("screen1", result!!.screens.first().id)
    }

    @Test
    fun parseScreens_invalidJson_returnsNull() {
        val json = "{invalid}"
        val result = JsonParser.parseScreens(json)
        assertNull(result)
    }
}
