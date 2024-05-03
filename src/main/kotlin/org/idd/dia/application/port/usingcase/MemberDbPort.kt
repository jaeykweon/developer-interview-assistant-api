package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.Member

interface MemberDbPort {
    fun save(entity: MemberEntity): MemberEntity

    fun exists(oauthId: Member.OauthId): Boolean

    fun getEntity(pk: Member.Pk): MemberEntity

    fun getEntity(oauthId: Member.OauthId): MemberEntity
}
