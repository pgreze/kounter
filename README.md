# Kounter

Project hosting an easy-to-use **Counter**
or more advanced use-cases with a type-safe **MapWithDefault**.

## Counter / MutableCounter

A specialization of the Map class allowing to count objects.

Usage:

```kotlin
val lints = mutableCounterOf("warnings" to 20)

lints["warnings"] += 3
lints["errors"] += 1

println(lints) // {warnings=23, errors=1}
```

Helpers:

```kotlin
counterOf("aabbbcc")                    // {a=2, b=3, c=2}
counterOf(arrayOf('a', 'b', 'b', 'c'))  // {a=1, b=2, c=1}
counterOf(listOf('a', 'a', 'b', 'c'))   // {a=2, b=1, c=1}
counterOf(setOf('a', 'b', 'c'))         // {a=1, b=1, c=1}
```

To perform mathematical operations on counters, use following methods:

```kotlin
val c1 = counterOf("ab")
val c2 = counterOf("ccd")
c1.plusAll(c2) // {a=2, b=3, c=2}

val chars = mutableCounterOf("ab")
chars.addAll(counterOf("ccd")) // {a=2, b=3, c=2}
```

Port of [Python Counter](https://docs.python.org/3.8/library/collections.html#collections.Counter),
inspired by [older references](https://github.com/python/cpython/blob/ec007cb43faf5f33d06efbc28152c7fdcb2edb9c/Lib/collections/__init__.py#L516)
like [Smalltalk Bag class](http://www.gnu.org/software/smalltalk/manual-base/html_node/Bag.html).

*Known limitation:* Only support Int for now.

## MapWithDefault / MutableMapWithDefault

This is internals of **Counter / MutableCounter**.

This is also an alternative of
[withDefault](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/with-default.html)
which is a memory efficient way to specify a default value for a Map but sadly,
not reflecting this change in its signature.<br/>
It's forcing users to use an unnatural
[getValue](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/get-value.html) extension method,
which is not the [most intuitive way of using a Map](https://discuss.kotlinlang.org/t/map-withdefault-not-defaulting/7691).

Proposed alternative is slightly changing the original Map interface:

```kotlin
interface MapWithDefault<K, V> : Map<K, V> {
    override fun get(key: K): V
}
```

Allowing to unlock a better syntax:

```kotlin
val money = mutableMapOf("Alice" to 5)
    .setDefault { 0 }

money["Alice"] += 10
money["Bob"] += 3

println(money) // {Alice=15, Bob=3}
```

Which is shining when used with Set/List/Map:

```kotlin
val sets = mutableMapOf<String, Set<String>>()
    .setDefault { setOf() }

sets += "Alice" to setOf("f1.txt")
sets["Bob"] += setOf("f2.md")

println(sets) // {"A0"= setOf("f1.txt"), "A1"= setOf("f2.md")}
```

⚠️ always use immutable collections if possible,
otherwise you may face resolution conflicts between main container and sub-ones.
