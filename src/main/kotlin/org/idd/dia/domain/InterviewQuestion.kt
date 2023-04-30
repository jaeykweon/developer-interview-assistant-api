package org.idd.dia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@JvmInline
value class InterviewQuestionPk(
    val value: Long = 0L
) {
    init {
        require(value >= 0)
    }
}

enum class OwnerShip {
    PUBLIC, PRIVATE
}

@JvmInline
value class InterviewQuestionTitle(
    val value: String
) {
    init {
        require(value.isNotBlank())
    }
}

@Entity
class InterviewQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: InterviewQuestionPk = InterviewQuestionPk(),

    @Column(updatable = false)
    val ownerPk: DiaUserPk,

    @Column(nullable = false)
    private var title: InterviewQuestionTitle,

    ownerShip: OwnerShip,

) : HistoryEntity() {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var ownerShip: OwnerShip = ownerShip
        private set

    @Column(nullable = false)
    var deleted: Boolean = false
        private set

    fun getTitle(requestUserPk: DiaUserPk?): InterviewQuestionTitle {
        if (ownerShip == OwnerShip.PUBLIC) return title

        if (ownerShip == OwnerShip.PRIVATE && ownerPk == requestUserPk) return title

        throw IllegalArgumentException()
    }
}
