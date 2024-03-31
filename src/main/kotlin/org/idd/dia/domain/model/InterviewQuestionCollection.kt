package org.idd.dia.domain.model

interface InterviewQuestionCollection {
    @JvmInline
    value class Pk(
        val value: Long,
    ) {
        init {
            require(value >= 0) { "value must be positive" }
        }
    }

    @JvmInline
    value class Title(
        val value: String,
    )
}
