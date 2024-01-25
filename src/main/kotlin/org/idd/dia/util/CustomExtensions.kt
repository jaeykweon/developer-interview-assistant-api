package org.idd.dia.util

inline fun <T, R> Iterable<T>.mapToSet(transform: (T) -> R): Set<R> = this.map(transform).toSet()
