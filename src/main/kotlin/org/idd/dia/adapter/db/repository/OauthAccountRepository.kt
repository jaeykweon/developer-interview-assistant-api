package org.idd.dia.adapter.db.repository

import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.entity.OauthAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class OauthAccountRepository(
    private val authJpaRepository: AuthJpaRepository,
) {
    fun getByAccessToken(accessToken: String): OauthAccountEntity =
        authJpaRepository.findByAccessToken(accessToken)
            ?: throw BadRequestException("잘못된 인증 토큰입니다")
}

interface AuthJpaRepository : JpaRepository<OauthAccountEntity, Long> {
    fun findByAccessToken(accessToken: String): OauthAccountEntity?
}
