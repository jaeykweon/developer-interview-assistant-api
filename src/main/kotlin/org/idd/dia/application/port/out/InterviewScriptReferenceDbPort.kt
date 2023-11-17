package org.idd.dia.application.port.out

import org.idd.dia.domain.MostQuotedReferencesOfQuestion
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScriptReference
import org.idd.dia.domain.Member

interface InterviewScriptReferenceDbPort {
    fun getReference(pk: InterviewScriptReference.Pk): InterviewScriptReference
    fun getReference(questionPk: InterviewQuestion.Pk): InterviewScriptReference
    fun getMostQuotedReference(questionPk: InterviewQuestion.Pk): MostQuotedReferencesOfQuestion
    fun save(interviewScriptReference: InterviewScriptReference): InterviewScriptReference
    fun delete(pk: InterviewScriptReference.Pk, memberPk: Member.Pk)
}
