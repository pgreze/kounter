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

    describe("setDefault with collections") {
        it("support Set<T>") {
            val sets = mutableMapOf<String, Set<String>>()
                .setDefault { setOf() }

            sets += "A0" to setOf("R0")
            sets["A1"] += setOf("R1")

            sets shouldBeEqualTo mapOf(
                "A0" to setOf("R0"),
                "A1" to setOf("R1")
            )
        }

        it("support List<T>") {
            val lists = mutableMapOf<String, List<String>>()
                .setDefault { listOf() }

            lists += "A0" to listOf("R0")
            lists["A1"] += listOf("R1")

            lists shouldBeEqualTo mapOf(
                "A0" to listOf("R0"),
                "A1" to listOf("R1")
            )
        }

        it("support Map<T>") {
            val maps = mutableMapOf<String, Map<String, Int>>()
                .setDefault { mapOf() }

            maps += "A0" to mapOf("Alice" to 1)
            maps["A1"] += mapOf("Bob" to 1)

            maps shouldBeEqualTo mapOf(
                "A0" to mapOf("Alice" to 1),
                "A1" to mapOf("Bob" to 1)
            )
        }

        it("support Counter") {
            val counters = mutableMapOf<String, Counter<String>>()
                .setDefault { counterOf() }

            counters += "A0" to counterOf("Alice" to 1)
            // Type mismatch:
            // Required: Counter<String> /* = MapWithDefault<String, Int> */
            // Found: Map<String, Int>
            // counters["A1"] += counterOf("Bob" to 1)

            counters shouldBeEqualTo mapOf(
                "A0" to mapOf("Alice" to 1)
            )
        }
    }

    describe("setDefault with mutable collections") {
        it("support MutableSet<T>") {
            val sets = mutableMapOf<String, MutableSet<String>>()
                .setDefault { mutableSetOf() }

            sets += "A0" to mutableSetOf("R0")
            // Assignment operators ambiguity:
            // public operator fun <T> Set<String>.plus(element: String): Set <String> defined In kotlin.collections
            // public Inline operator fun <T> MutableCollection<in String>.plusAssign(element: String): Unit defined in kotlin.collections
            // sets["A1"] += setOf("R1")

            sets shouldBeEqualTo mapOf(
                "A0" to setOf("R0")
            )
        }

        it("support List<T>") {
            val lists = mutableMapOf<String, MutableList<String>>()
                .setDefault { mutableListOf() }

            lists += "A0" to mutableListOf("R0")
            // lists["A1"] += mutableListOf("R1")

            lists shouldBeEqualTo mapOf(
                "A0" to listOf("R0")
            )
        }

        it("support Map<T>") {
            val maps = mutableMapOf<String, MutableMap<String, Int>>()
                .setDefault { mutableMapOf() }

            maps += "A0" to mutableMapOf("Alice" to 1)
            // maps["A1"] += mutableMapOf("Bob" to 1)

            maps shouldBeEqualTo mapOf(
                "A0" to mapOf("Alice" to 1)
            )
        }

        it("support MutableCounter") {
            val counters = mutableMapOf<String, MutableCounter<String>>()
                .setDefault { mutableCounterOf() }

            counters += "A0" to mutableCounterOf("Alice" to 1)
            // counters["A1"] += mutableCounterOf("Bob" to 1)

            counters shouldBeEqualTo mapOf(
                "A0" to mapOf("Alice" to 1)
            )
        }
    }
})
