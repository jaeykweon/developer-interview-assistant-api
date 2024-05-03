package org.idd.dia.domain.model

interface InterviewQuestion {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class Title(
        val value: String,
    ) {
        init {
            require(value.isNotEmpty())
        }
    }
}
