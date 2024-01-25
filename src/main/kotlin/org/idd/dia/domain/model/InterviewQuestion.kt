package org.idd.dia.domain.model

class InterviewQuestion {
    @JvmInline
    value class Pk(
        val value: Long = 0L,
    ) {
        companion object {
            @JvmStatic
            fun max(): Pk = Pk(Long.MAX_VALUE)
        }
    }

    @JvmInline
    value class Title(
        val value: String,
    ) {
        init {
            require(value.length >= 0)
        }
    }
}
