package org.idd.dia.domain.model

class InterviewPracticeHistory {
    @JvmInline
    value class Pk(
        val value: Long,
    ) {
        companion object {
            @JvmStatic
            fun new(): Pk = Pk(0L)

            @JvmStatic
            fun max(): Pk = Pk(Long.MAX_VALUE)
        }
    }

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
