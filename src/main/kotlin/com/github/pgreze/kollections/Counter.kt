/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.github.pgreze.kollections

typealias Counter<T> = MapWithDefault<T, Int>

fun <T> Map<T, Int>.toCounter(): Counter<T> =
    setDefault { 0 }

fun <T> counterOf(): Counter<T> =
    mapOf<T, Int>().toCounter()

fun <T> counterOf(vararg keys: Pair<T, Int>): Counter<T> =
    keys.toMap(mutableMapOf()).toCounter()

fun counterOf(s: CharSequence): Counter<Char> =
    counterOf(s.toList())

fun <T> counterOf(items: Array<T>): Counter<T> =
    mutableCounterOf(items).toCounter()

fun <T> counterOf(items: Iterator<T>): Counter<T> =
    mutableCounterOf(items).toCounter()

fun <T> counterOf(items: Iterable<T>): Counter<T> =
    mutableCounterOf(items).toCounter()

fun <T> Counter<T>.toMutableCounter(): MutableCounter<T> =
    MutableCounterImpl(toMutableMap().setDefault { 0 })

fun <T> Counter<T>.plusAll(map: Map<T, Int>): Counter<T> =
    (keys + map.keys).map { k ->
        k to this.getOrDefault(k, 0) + map.getOrDefault(k, 0)
    }.toMap().toCounter()
