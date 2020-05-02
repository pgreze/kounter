package com.github.pgreze.kollections

// Multi List with default

fun <K, V> multiListWithDefaultOf(
    vararg entries: Pair<K, List<V>>,
    defaultValue: (key: K) -> List<V> = { listOf() }
): MapWithDefault<K, List<V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, V> mutableMultiListWithDefaultOf(
    vararg entries: Pair<K, List<V>>,
    defaultValue: (key: K) -> List<V> = { listOf() }
): MutableMapWithDefault<K, List<V>> =
    mutableMapOf(*entries).setDefault(defaultValue)

// Multi Set with default

fun <K, V> multiSetWithDefaultOf(
    vararg entries: Pair<K, Set<V>>,
    defaultValue: (key: K) -> Set<V> = { setOf() }
): MapWithDefault<K, Set<V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, V> mutableMultiSetWithDefaultOf(
    vararg entries: Pair<K, Set<V>>,
    defaultValue: (key: K) -> Set<V> = { setOf() }
): MutableMapWithDefault<K, Set<V>> =
    mutableMapOf(*entries).setDefault(defaultValue)

// Multi Map with default

fun <K, SK, V> multiMapWithDefaultOf(
    vararg entries: Pair<K, Map<SK, V>>,
    defaultValue: (key: K) -> Map<SK, V> = { mapOf() }
): MapWithDefault<K, Map<SK, V>> =
    mapOf(*entries).setDefault(defaultValue)

fun <K, SK, V> mutableMultiMapWithDefaultOf(
    vararg entries: Pair<K, Map<SK, V>>,
    defaultValue: (key: K) -> Map<SK, V> = { mapOf() }
): MutableMapWithDefault<K, Map<SK, V>> =
    mutableMapOf(*entries).setDefault(defaultValue)
