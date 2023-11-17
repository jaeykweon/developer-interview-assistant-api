package org.idd.dia.util

inline fun <T, R> Iterable<T>.mapToSet(transform: (T) -> R): Set<R> {
    return this.map(transform).toSet()
}
