package org.idd.dia.domain.model

class InterviewScriptReference(
    private val pk: Pk,
    private val ownerPk: Member.Pk,
    private val questionPk: InterviewQuestion.Pk,
    private val scriptPk: InterviewScript.Pk,
    private val url: Url,
    private val clickCount: ClickCount = ClickCount(),
) {
    fun getPk() = this.pk

    fun getOwnerPk() = this.ownerPk

    fun getQuestionPk() = this.questionPk

    fun getScriptPk() = this.scriptPk

    fun getUrl() = this.url

    fun getClickCount() = this.clickCount

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
