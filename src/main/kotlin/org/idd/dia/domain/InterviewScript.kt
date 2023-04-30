package org.idd.dia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@JvmInline
value class InterviewScriptPk(
    val value: Long = 0L
) {
    init {
        require(value >= 0)
    }
}

@JvmInline
value class InterviewScriptContent(
    val value: String
) {
    init {
        require(value.isNotBlank())
    }
}

@Entity
class InterviewScript(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: InterviewScriptPk = InterviewScriptPk(),

    @Column(updatable = false, nullable = false)
    val userPk: DiaUserPk,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    val question: InterviewQuestion,

    @Column(length = 2000)
    private var content: InterviewScriptContent
) {
    fun getContent(requestUserPk: DiaUserPk): InterviewScriptContent {
        if (requestUserPk != this.userPk) throw IllegalArgumentException()

        return content
    }
}
