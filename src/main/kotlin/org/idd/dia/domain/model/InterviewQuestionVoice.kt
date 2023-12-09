package org.idd.dia.domain.model

class InterviewQuestionVoice(
    private val pk: Pk,
    private val questionPk: InterviewQuestion.Pk,
    private val gender: Gender,
    private val filePath: FilePath,
    private val subtitle: SubTitle
) {
    fun getPk() = this.pk
    fun getQuestionPk() = this.questionPk
    fun getGender() = this.gender
    fun getFilePath() = this.filePath
    fun getSubtitle() = this.subtitle

    @JvmInline
    value class Pk(
        val value: Long
    )

    enum class Gender {
        MALE,
        FEMALE;

        companion object {
            fun from(string: String): Gender {
                return valueOf(string)
            }
        }
    }

    @JvmInline
    value class FilePath(
        val value: String
    )

    /**
     * 이 음성을 만들 때 사용한 자막
     */
    @JvmInline
    value class SubTitle(
        val value: String
    )
}
