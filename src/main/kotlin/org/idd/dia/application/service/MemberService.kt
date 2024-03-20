package org.idd.dia.application.service

import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberDbPort: MemberDbPort,
) {
    fun getMember(pk: Member.Pk): MemberEntity {
        return memberDbPort.getEntity(pk)
    }
}
