package org.idd.dia.domain.model

interface InterviewQuestionCollection {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class Title(
        val value: String,
    )
}
