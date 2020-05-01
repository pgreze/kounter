package com.github.pgreze.kollections

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MutableCounterTest : Spek({

    describe("a mutable counter") {
        val counter = mutableCounterOf(
            "Bob" to 10,
            "Charlie" to 5,
            "Dave" to 8
        )

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

    describe("2 counters") {
        val c1 = mutableCounterOf(arrayOf("a", "b", "b"))
        val c2 = counterOf(listOf("b", "c", "c"))

        it("support addAll") {
            c1.apply { addAll(c2) } shouldBeEqualTo mapOf(
                "a" to 1,
                "b" to 3,
                "c" to 2
            )
        }
    }

    describe("mutableCounterOf") {
        it("support CharSequence") {
            mutableCounterOf("abbccc") shouldBeEqualTo mapOf(
                'a' to 1,
                'b' to 2,
                'c' to 3
            )
        }
        it("support Array") {
            mutableCounterOf(arrayOf('a', 'b', 'b', 'c', 'c', 'c')) shouldBeEqualTo mapOf(
                'a' to 1,
                'b' to 2,
                'c' to 3
            )
        }
        it("support list") {
            mutableCounterOf(listOf('a', 'b', 'b', 'c', 'c', 'c')) shouldBeEqualTo mapOf(
                'a' to 1,
                'b' to 2,
                'c' to 3
            )
        }
        it("support set") {
            mutableCounterOf(setOf('a', 'b', 'c')) shouldBeEqualTo mapOf(
                'a' to 1,
                'b' to 1,
                'c' to 1
            )
        }
        it("support map") {
            mutableCounterOf(iterator {
                yield("Alice")
                yield("Bob")
                yield("Bob")
            }) shouldBeEqualTo mapOf(
                "Alice" to 1,
                "Bob" to 2
            )
        }
    }
})
