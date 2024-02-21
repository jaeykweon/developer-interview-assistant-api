package org.idd.dia.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

inline fun <T, R> Iterable<T>.mapToSet(transform: (T) -> R): Set<R> = this.map(transform).toSet()

@OptIn(ExperimentalContracts::class)
fun Any?.isNull(): Boolean {
    contract {
        returns(false) implies (this@isNull != null)
        returns(true) implies (this@isNull == null)
    }
    return this == null
}

@OptIn(ExperimentalContracts::class)
fun Any?.isNotNull(): Boolean {
    contract {
        returns(true) implies (this@isNotNull != null)
    }
    return this != null
}
