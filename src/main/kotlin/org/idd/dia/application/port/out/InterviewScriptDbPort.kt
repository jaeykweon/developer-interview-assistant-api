package org.idd.dia.application.port.out

import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.idd.dia.domain.Member

interface InterviewScriptDbPort {
    fun isExists(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): Boolean
    fun save(interviewScript: InterviewScript): InterviewScript
    fun get(pk: InterviewScript.Pk): InterviewScript
    fun get(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): InterviewScript
}
