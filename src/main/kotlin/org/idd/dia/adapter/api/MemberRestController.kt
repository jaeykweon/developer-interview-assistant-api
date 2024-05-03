package org.idd.dia.adapter.api

import org.idd.dia.adapter.config.RequestAuth
import org.idd.dia.application.dto.MemberInfoViewModel
import org.idd.dia.application.port.usecase.MemberServiceUseCase
import org.idd.dia.domain.model.Member
import org.springframework.web.bind.annotation.GetMapping

@ApiV0RestController
class MemberRestController(
    private val memberServiceUseCase: MemberServiceUseCase,
) {
    @GetMapping("/members/me")
    fun getMemberInfo(
        @RequestAuth memberPk: Member.Pk,
    ): ApiResponse<MemberInfoViewModel> {
        val memberInfo = memberServiceUseCase.getMemberInfo(memberPk)
        return ApiResponse.ok(memberInfo)
    }
}
