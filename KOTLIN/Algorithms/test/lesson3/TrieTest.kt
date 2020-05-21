package lesson3

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class TrieTest : AbstractTrieTest() {

    override fun create(): MutableSet<String> =
        Trie()

    @Test
    @Tag("Example")
    fun generalTest() {
        doGeneralTest()
    }

    @Test
    @Tag("Hard")
    fun iteratorTest() {
        doIteratorTest()
    }

    @Test
    @Tag("Hard")
    fun iteratorRemoveTest() {
        doIteratorRemoveTest()
    }

}