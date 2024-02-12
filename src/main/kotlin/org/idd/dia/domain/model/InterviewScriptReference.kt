package org.idd.dia.domain.model

interface InterviewScriptReference {
    @JvmInline
    value class Pk(
        val value: Long = 0L,
    )

    @JvmInline
    value class Url(
        val value: String,
    )

    @JvmInline
    value class ClickCount(
        val value: Long = 0L,
    ) {
        init {
            require(value >= 0) { "클릭 횟수는 양수여야 합니다." }
        }
    }
}
