import kotlin.test.Test
import kotlin.test.assertEquals
import org.sacada.data.util.convertToCamelCase
import org.sacada.data.util.convertToPascalCase
import org.sacada.data.util.lowercaseFirstLetter

class DataExtensionsTest {
    @Test
    fun testCamelCaseConversion() {
        assertEquals("MyExample", "MY_EXAMPLE".convertToCamelCase())
        assertEquals("helloWorld", "hello_world".convertToPascalCase())
    }

    @Test
    fun testLowercaseFirstLetter() {
        assertEquals("example", "Example".lowercaseFirstLetter())
    }
}
