package com.github.pgreze.kollections

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MutableMapWithDefaultTest : Spek({

    describe("a mutableMap with setDefault") {
        val counter = mutableMapOf(
            "Bob" to 10,
            "Charlie" to 5,
            "Dave" to 8
        ).setDefault { 0 }

        it("support +-*/= notations") {
            counter["Alice"] += 20
            counter["Bob"] -= 15
            counter["Charlie"] *= 6
            counter["Dave"] /= 2

            counter.toSortedMap() shouldBeEqualTo mapOf(
                "Alice" to 20,
                "Bob" to -5,
                "Charlie" to 30,
                "Dave" to 4
            )
        }
    }
})
