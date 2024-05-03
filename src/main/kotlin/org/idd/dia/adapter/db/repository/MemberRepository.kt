package org.idd.dia.adapter.db.repository

import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberDbPort {
    override fun save(entity: MemberEntity): MemberEntity = memberJpaRepository.save(entity)

    override fun exists(oauthId: Member.OauthId): Boolean {
        return memberJpaRepository.existsByOauthIdValue(oauthId.value)
    }

    override fun getEntity(pk: Member.Pk): MemberEntity =
        memberJpaRepository.findByIdOrNull(pk.value)
            ?: throw NotFoundException("해당 회원 정보가 없습니다")

    override fun getEntity(oauthId: Member.OauthId): MemberEntity =
        memberJpaRepository.findByOauthIdValue(oauthId.value)
            ?: throw NotFoundException("해당 회원 정보가 없습니다")
}

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun findByOauthIdValue(githubIdValue: String): MemberEntity?

    fun existsByOauthIdValue(githubIdValue: String): Boolean
}
