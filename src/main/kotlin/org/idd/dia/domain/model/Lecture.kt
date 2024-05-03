package org.idd.dia.domain.model

interface Lecture {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class Url(
        val value: String,
    )

    @JvmInline
    value class Price(
        val value: Int,
    ) {
        init {
            require(value >= 0)
        }
    }
}
