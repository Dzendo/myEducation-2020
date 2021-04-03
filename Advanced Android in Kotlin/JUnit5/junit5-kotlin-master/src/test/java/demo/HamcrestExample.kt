package demo

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test

class HamcrestExample {

    @Test
    fun `Some examples`() {
        val list = listOf("s1", "s2", "s3")
        assertThat(list, containsInAnyOrder("s3", "s1", "s2"))
        assertThat(list, hasItem("s1"))
        assertThat(list.size, greaterThanOrEqualTo(3))
        assertThat(list[0], notNullValue())
    }
}