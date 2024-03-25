package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.MemberInfoViewModel
import org.idd.dia.domain.model.Member

interface MemberServiceUseCase {
    fun getMemberInfo(pk: Member.Pk): MemberInfoViewModel
}
