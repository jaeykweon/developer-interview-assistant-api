package org.idd.dia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@JvmInline
value class InterviewRecordPk(
    val value: Long = 0L
) {
    init {
        require(value >= 0)
    }
}

enum class RecordType {
    PRACTICE, REHEARSAL
}

@Entity
class InterviewRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val pk: InterviewRecordPk = InterviewRecordPk(),

    @Column(updatable = false, nullable = false)
    val ownerPk: DiaUserPk,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val question: InterviewQuestion,

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    val type: RecordType,

    @Column(length = 2000)
    private var content: String,

) : HistoryEntity() {

    @Column(nullable = false)
    var deleted: Boolean = false
        private set
}
