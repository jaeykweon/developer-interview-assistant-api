package org.idd.dia.domain.model

interface InterviewQuestionVoice {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class FilePath(
        val value: String,
    )

    /** 음성을 만들 때 사용한 자막 */
    @JvmInline
    value class SubTitle(
        val value: String,
    )
}
