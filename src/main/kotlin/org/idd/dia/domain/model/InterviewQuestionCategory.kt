package org.idd.dia.domain.model

interface InterviewQuestionCategory {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class Title(
        val value: String,
    )
}
