package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.MemberTokenEntity
import org.idd.dia.domain.model.MemberToken

interface MemberTokenDbPort {
    fun save(memberToken: MemberTokenEntity): MemberTokenEntity

    fun getEntity(accessToken: MemberToken.AccessToken): MemberTokenEntity
}
