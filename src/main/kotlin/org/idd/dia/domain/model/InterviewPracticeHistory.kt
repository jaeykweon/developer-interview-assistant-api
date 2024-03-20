package org.idd.dia.domain.model

interface InterviewPracticeHistory {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class Content(
        val value: String,
    )

    enum class Type {
        /** 개별 질문 연습 */
        SINGLE,

        /** 여러 질문 연습 */
        MULTI,
    }

    /** 연습한 시간(초) */
    @JvmInline
    value class ElapsedTime(
        val value: Int,
    ) {
        init {
            require(value > 0) { "elapsedTime must be more than 0 sec" }
        }
    }

    @JvmInline
    value class FilePath(
        val value: String,
    )
}
