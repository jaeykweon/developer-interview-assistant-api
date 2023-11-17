package org.idd.dia.domain

class InterviewQuestionVoice(
    private val pk: Pk,
    private val questionPk: InterviewQuestion.Pk,
    private val gender: Gender,
    private val filePath: FilePath
) {
    fun getPk() = this.pk
    fun getQuestionPk() = this.questionPk
    fun getGender() = this.gender
    fun getFilePath() = this.filePath

    @JvmInline
    value class Pk(
        val value: Long
    )

    enum class Gender {
        MALE,
        FEMALE
    }

    @JvmInline
    value class FilePath(
        val value: String
    )
}
