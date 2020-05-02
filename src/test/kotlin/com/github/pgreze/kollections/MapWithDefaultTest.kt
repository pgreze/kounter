package com.github.pgreze.kollections

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MapWithDefaultTest : Spek({

    describe("a map with setDefault") {
        val m = mapOf("Alice" to 1).setDefault { 0 }

        it("return value for known values") {
            m["Alice"] shouldBeEqualTo 1
        }
        it("return default for unknown values") {
            m["Bob"] shouldBeEqualTo 0
        }
    }
})
