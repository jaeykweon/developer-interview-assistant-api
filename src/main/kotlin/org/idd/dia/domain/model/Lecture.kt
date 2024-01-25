package org.idd.dia.domain.model

class Lecture(
    private val pk: Pk,
    private val url: Url,
    private val price: Price,
) {
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
