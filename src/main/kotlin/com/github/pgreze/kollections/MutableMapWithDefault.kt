package com.github.pgreze.kollections

interface MutableMapWithDefault<K, V> :
    MapWithDefault<K, V>,
    MutableMap<K, V>

fun <K, V> MutableMap<K, V>.setDefault(
    defaultValue: (key: K) -> V
): MutableMapWithDefault<K, V> =
    MutableMapWithDefaultImpl(toMutableMap(), defaultValue)

private class MutableMapWithDefaultImpl<K, V>(
    private val map: MutableMap<K, V>,
    private val defaultValue: (key: K) -> V
) : MutableMapWithDefault<K, V>, MutableMap<K, V> by map {
    override fun equals(other: Any?): Boolean = map == other
    override fun hashCode(): Int = map.hashCode()
    override fun toString(): String = map.toString()

    override fun get(key: K): V = map[key] ?: defaultValue(key)
}
