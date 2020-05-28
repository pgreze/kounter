package com.github.pgreze.kollections

import com.github.pgreze.kounter.Counter
import com.github.pgreze.kounter.counterOf
import com.github.pgreze.kounter.mutableCounterOf
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@Suppress("RemoveExplicitTypeArguments")
class MultiCollectionsTest : Spek({

    describe("a multiSet") {
        it("is immutable") {
            multiSetWithDefaultOf<String, Int>() shouldBeEqualTo mapOf<String, Int>()

            multiSetWithDefaultOf<String, Int> { mutableSetOf() } shouldBeEqualTo mapOf<String, Int>()

            multiSetWithDefaultOf("Alice" to setOf(1, 2)) shouldBeEqualTo mapOf(
                "Alice" to setOf(1, 2)
            )
        }

        it("can be mutable") {
            val sets = mutableMultiSetWithDefaultOf<String, String>()

            sets += "A0" to setOf("R0")
            sets["A1"] += setOf("R1")

            sets shouldBeEqualTo mapOf(
                "A0" to setOf("R0"),
                "A1" to setOf("R1")
            )
        }
    }

    describe("a multiList") {
        it("is immutable") {
            multiListWithDefaultOf<String, Int>() shouldBeEqualTo mapOf<String, Int>()

            multiListWithDefaultOf<String, Int>(defaultValue = { mutableListOf() }) shouldBeEqualTo
                mapOf<String, Int>()

            multiListWithDefaultOf("Alice" to listOf(1, 2)) shouldBeEqualTo mapOf(
                "Alice" to listOf(1, 2)
            )
        }

        it("can be mutable") {
            val lists = mutableMultiListWithDefaultOf<String, String>()

            lists += "A0" to listOf("R0")
            lists["A1"] += listOf("R1")

            lists shouldBeEqualTo mapOf(
                "A0" to listOf("R0"),
                "A1" to listOf("R1")
            )
        }
    }

    describe("a multiMap") {
        it("is immutable") {
            multiMapWithDefaultOf<String, Int, Boolean>() shouldBeEqualTo mapOf<String, Map<Int, Boolean>>()

            multiMapWithDefaultOf<Boolean, String, Int> { counterOf() } shouldBeEqualTo
                mapOf<String, Map<Int, Boolean>>()

            multiMapWithDefaultOf("Alice" to mapOf(true to 2)) shouldBeEqualTo mapOf(
                "Alice" to mapOf(true to 2)
            )
        }

        it("can be mutable") {
            val maps = mutableMultiMapWithDefaultOf<String, String, Int>()

            maps += "A0" to mapOf("Alice" to 1)
            maps["A1"] += mapOf("Bob" to 1)
            // maps["A1"]["Bob"] += 1 // No set method providing array access

            maps shouldBeEqualTo mapOf(
                "A0" to mapOf("Alice" to 1),
                "A1" to mapOf("Bob" to 1)
            )
        }
    }

    describe("a multiCounter") {
        it("is immutable") {
            multiCounterWithDefaultOf<Boolean, String>() shouldBeEqualTo mapOf<Boolean, Counter<String>>()

            multiCounterWithDefaultOf<Boolean, String>(defaultValue = { mutableCounterOf() }) shouldBeEqualTo
                mapOf<Boolean, Counter<String>>()

            multiCounterWithDefaultOf("Alice" to counterOf(true to 2)) shouldBeEqualTo mapOf(
                "Alice" to counterOf(true to 2)
            )
        }

        it("can be mutable") {
            val counters = mutableMultiCounterWithDefaultOf<String, String>()

            counters += "A0" to counterOf("Alice" to 1)
            // counters["A1"] += counterOf("Bob" to 1) // Type mismatch:
            // Required: Counter<String> /* = MapWithDefault<String, Int> */
            // Found: Map<String, Int>

            counters shouldBeEqualTo mapOf(
                "A0" to counterOf("Alice" to 1)
            )
        }
    }
})

// Multi Counter With Default (only for reference)
// better use a multiMapWithDefaultOf / mutableMultiMapWithDefaultOf because of the Type Mismatch.

fun <K, V> multiCounterWithDefaultOf(
    vararg entries: Pair<K, Counter<V>>,
    defaultValue: (key: K) -> Counter<V> = { counterOf() }
): MapWithDefault<K, Counter<V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, V> mutableMultiCounterWithDefaultOf(
    vararg entries: Pair<K, Counter<V>>,
    defaultValue: (key: K) -> Counter<V> = { counterOf() }
): MutableMapWithDefault<K, Counter<V>> =
    mutableMapOf(*entries).setDefault(defaultValue)
