package org.idd.dia.domain.entity

import java.time.Instant
import javax.persistence.Entity

@Entity
class MemberTokenEntity(
    pk: Long,
    val deletedAt: Instant

): DbEntity(pk) {
}
