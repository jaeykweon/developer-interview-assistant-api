package org.idd.dia.adapter.db.repository

import org.idd.dia.application.port.usingcase.MemberTokenDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.MemberTokenEntity
import org.idd.dia.domain.model.MemberToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class MemberTokenRepository(
    private val memberTokenJpaRepository: MemberTokenJpaRepository,
) : MemberTokenDbPort {
    override fun save(memberToken: MemberTokenEntity): MemberTokenEntity {
        return memberTokenJpaRepository.save(memberToken)
    }

    override fun getEntity(accessToken: MemberToken.AccessToken): MemberTokenEntity {
        return memberTokenJpaRepository.findByAccessTokenValue(accessToken.value)
            ?: throw NotFoundException("없는 토큰입니다")
    }
}

interface MemberTokenJpaRepository : JpaRepository<MemberTokenEntity, Long> {
    fun findByAccessTokenValue(accessToken: String): MemberTokenEntity?
}
