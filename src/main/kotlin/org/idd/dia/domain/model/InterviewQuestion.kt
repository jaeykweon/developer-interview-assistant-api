package org.idd.dia.domain.model

class InterviewQuestion(
    private val pk: Pk,
    private val title: Title,
) {
    fun getPk() = this.pk
    fun getTitle() = this.title

    @JvmInline
    value class Pk(
        val value: Long = 0L
    )

    @JvmInline
    value class Title(
        val value: String
    ) {
        init {
            require(value.length >= 0)
        }
    }
}
