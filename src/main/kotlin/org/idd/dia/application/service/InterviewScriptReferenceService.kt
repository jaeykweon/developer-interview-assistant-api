package org.idd.dia.application.service

import org.idd.dia.application.dto.MostQuotedReferencesOfQuestion
import org.idd.dia.application.port.`in`.InterviewScriptReferenceServiceUseCase
import org.idd.dia.application.port.out.InterviewScriptReferenceDbPort
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScriptReference
import org.idd.dia.domain.Member
import org.springframework.stereotype.Service

@Service
class InterviewScriptReferenceService(
    private val interviewScriptReferenceDbPort: InterviewScriptReferenceDbPort
) : InterviewScriptReferenceServiceUseCase {

    override fun register(
        interviewScriptReference: InterviewScriptReference
    ): InterviewScriptReference {
        return interviewScriptReferenceDbPort.save(interviewScriptReference)
    }

    override fun getReference(
        interviewQuestionPk: InterviewQuestion.Pk
    ): InterviewScriptReference {
        return interviewScriptReferenceDbPort.getReference(interviewQuestionPk)
    }

    override fun getMostQuotedReferences(
        interviewQuestionPk: InterviewQuestion.Pk
    ): MostQuotedReferencesOfQuestion {
        return interviewScriptReferenceDbPort.getMostQuotedReference(interviewQuestionPk)
    }

    override fun addClickCount(
        interviewScriptReferencePk: InterviewScriptReference.Pk
    ): InterviewScriptReference {
        val added: InterviewScriptReference =
            interviewScriptReferenceDbPort
                .getReference(interviewScriptReferencePk)
        return interviewScriptReferenceDbPort.save(added)
    }

    override fun delete(
        interviewScriptReferencePk: InterviewScriptReference.Pk,
        requestMemberPk: Member.Pk
    ) {
        interviewScriptReferenceDbPort.delete(interviewScriptReferencePk, requestMemberPk)
    }
}
