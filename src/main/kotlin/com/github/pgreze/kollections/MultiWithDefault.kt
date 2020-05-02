package com.github.pgreze.kollections

// Multi List

fun <K, V> multiListOf(
    vararg entries: Pair<K, List<V>>,
    defaultValue: (key: K) -> List<V> = { listOf() }
): MapWithDefault<K, List<V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, V> mutableMultiListOf(
    vararg entries: Pair<K, List<V>>,
    defaultValue: (key: K) -> List<V> = { listOf() }
): MutableMapWithDefault<K, List<V>> =
    mutableMapOf(*entries).setDefault(defaultValue)

// Multi Set

fun <K, V> multiSetOf(
    vararg entries: Pair<K, Set<V>>,
    defaultValue: (key: K) -> Set<V> = { setOf() }
): MapWithDefault<K, Set<V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, V> mutableMultiSetOf(
    vararg entries: Pair<K, Set<V>>,
    defaultValue: (key: K) -> Set<V> = { setOf() }
): MutableMapWithDefault<K, Set<V>> =
    mutableMapOf(*entries).setDefault(defaultValue)

// Multi Map

fun <K, SK, V> multiMapOf(
    vararg entries: Pair<K, Map<SK, V>>,
    defaultValue: (key: K) -> Map<SK, V> = { mapOf() }
): MapWithDefault<K, Map<SK, V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, SK, V> mutableMultiMapOf(
    vararg entries: Pair<K, Map<SK, V>>,
    defaultValue: (key: K) -> Map<SK, V> = { mapOf() }
): MutableMapWithDefault<K, Map<SK, V>> =
    mutableMapOf(*entries).setDefault(defaultValue)
