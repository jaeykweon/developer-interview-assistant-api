package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.adapter.db.repository.OauthAccountRepository
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService(
    private val oauthAccountRepository: OauthAccountRepository,
) {
    fun getMemberPkByAccessToken(accessToken: String): Member.Pk = oauthAccountRepository.getByAccessToken(accessToken).member.getPk()
}
