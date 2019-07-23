import org.junit.Assert.assertThat
import kotlin.test.assertEquals
import org.junit.Test as test

class TestSource {
    @test fun f() {
        val example  = Greeter("Hi")
        val greeting = example.greeting

        assertEquals("Hi", greeting)

    }
}
