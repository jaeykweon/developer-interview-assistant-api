package org.idd.dia.application.port.`in`

import org.idd.dia.application.dto.MostQuotedReferencesOfQuestion
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScriptReference
import org.idd.dia.domain.Member

interface InterviewScriptReferenceServiceUseCase {
    fun register(interviewScriptReference: InterviewScriptReference): InterviewScriptReference
    fun getReference(interviewQuestionPk: InterviewQuestion.Pk): InterviewScriptReference
    fun getMostQuotedReferences(interviewQuestionPk: InterviewQuestion.Pk): MostQuotedReferencesOfQuestion
    fun addClickCount(interviewScriptReferencePk: InterviewScriptReference.Pk): InterviewScriptReference
    fun delete(interviewScriptReferencePk: InterviewScriptReference.Pk, requestMemberPk: Member.Pk)
}
