package org.idd.dia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@JvmInline
value class DiaUserPk(
    val value: Long = 0L
) {
    init {
        require(value >= 0)
    }
}

@JvmInline
value class GithubId(
    val value: String
) {
    init {
        require(value.isNotBlank())
    }
}

@Entity
class DiaUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: DiaUserPk = DiaUserPk(),

    @Column(nullable = false)
    val githubId: GithubId,

) : HistoryEntity() {

    @Column(nullable = false)
    var deleted: Boolean = false
        private set
}
