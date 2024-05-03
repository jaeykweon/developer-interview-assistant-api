package org.idd.dia.domain.model

interface InterviewScript {
    @JvmInline
    value class Pk(
        val value: Long = 0L,
    )

    @JvmInline
    value class Content(
        val value: String,
    ) {
        init {
            require(value.length < 10000)
        }
    }
}
