package com.github.pgreze.kollections

/**
 * Mutable alternative of [MapWithDefault].
 */
interface MutableMapWithDefault<K, V> :
    MapWithDefault<K, V>,
    MutableMap<K, V>

/**
 * Create a new [MutableMapWithDefault] from given map
 * and for each unknown key, returns the value provided by [defaultValue] provider.
 */
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
