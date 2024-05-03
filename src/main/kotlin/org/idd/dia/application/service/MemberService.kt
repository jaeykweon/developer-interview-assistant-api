package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.MemberInfoViewModel
import org.idd.dia.application.port.usecase.MemberServiceUseCase
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberService(
    private val memberDbPort: MemberDbPort,
) : MemberServiceUseCase {
    override fun getMemberInfo(pk: Member.Pk): MemberInfoViewModel {
        val entity = memberDbPort.getEntity(pk)
        return MemberInfoViewModel(entity)
    }
}
