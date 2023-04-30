package org.idd.dia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@JvmInline
value class InterviewQuestionVoicePk(
    val value: Long = 0L
) {
    init {
        require(value >= 0)
    }
}

@Entity
class InterviewQuestionVoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: InterviewQuestionVoicePk = InterviewQuestionVoicePk(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val question: InterviewQuestion,

    @Column
    val url: String,
)
