package com.github.pgreze.kounter

import com.github.pgreze.kollections.MutableMapWithDefault
import com.github.pgreze.kollections.setDefault

/**
 * Mutable alternative of [Counter],
 * also bringing the convenient [addAll] method.
 */
interface MutableCounter<T> : Counter<T>,
    MutableMapWithDefault<T, Int> {
    /** Add all occurrences of [from] keys into this counter. */
    fun addAll(from: Map<out T, Int>)
}

fun <T> mutableCounterOf(): MutableCounter<T> =
    MutableCounterImpl()

fun <T> mutableCounterOf(vararg keys: Pair<T, Int>): MutableCounter<T> =
    MutableCounterImpl(keys.toMap(mutableMapOf()).setDefault { 0 })

fun <T> mutableCounterOf(items: Iterator<T>): MutableCounter<T> =
    MutableCounterImpl<T>().also { c -> items.forEach { c[it]++ } }

fun <T> mutableCounterOf(items: Array<T>): MutableCounter<T> =
    mutableCounterOf(items.iterator())

fun <T> mutableCounterOf(items: Iterable<T>): MutableCounter<T> =
    mutableCounterOf(items.iterator())

fun mutableCounterOf(s: CharSequence): MutableCounter<Char> =
    mutableCounterOf(s.toList())

internal class MutableCounterImpl<T>(
    private val map: MutableMapWithDefault<T, Int> =
        mutableMapOf<T, Int>().setDefault { 0 }
) : MutableCounter<T>, MutableMapWithDefault<T, Int> by map {
    override fun equals(other: Any?): Boolean = map == other
    override fun hashCode(): Int = map.hashCode()
    override fun toString(): String = map.toString()

    override fun addAll(from: Map<out T, Int>) =
        from.forEach { (k, c) -> this[k] += c }
}
