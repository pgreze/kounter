package com.github.pgreze.kollections

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MapWithDefaultTest : Spek({

    describe("a map with setDefault") {
        val map = mapOf("Alice" to 1)
        val mapWithDefault = map.setDefault { 0 }

        it("return value for known values") {
            mapWithDefault["Alice"] shouldBeEqualTo 1
        }
        it("return default for unknown values") {
            mapWithDefault["Bob"] shouldBeEqualTo 0
        }
        it("is equivalent to the original map") {
            mapWithDefault shouldBeEqualToOriginalMap map
        }
    }
})

infix fun <K, V> MapWithDefault<K, V>.shouldBeEqualToOriginalMap(map: Map<K, V>) {
    this shouldBeEqualTo map
    this.hashCode() shouldBeEqualTo map.hashCode()
    this.toString() shouldBeEqualTo map.toString()
}
