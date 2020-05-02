package com.github.pgreze.kollections

interface MapWithDefault<K, V> : Map<K, V> {
    override fun get(key: K): V
}

fun <K, V> Map<K, V>.setDefault(
    defaultValue: (key: K) -> V
): MapWithDefault<K, V> =
    MapWithDefaultImpl(toMap(), defaultValue)

private class MapWithDefaultImpl<K, V>(
    private val map: Map<K, V> = mapOf(),
    private val defaultValue: (key: K) -> V
) : MapWithDefault<K, V>, Map<K, V> by map {
    override fun equals(other: Any?): Boolean = map == other
    override fun hashCode(): Int = map.hashCode()
    override fun toString(): String = map.toString()

    override fun get(key: K): V = map[key] ?: defaultValue(key)
}
