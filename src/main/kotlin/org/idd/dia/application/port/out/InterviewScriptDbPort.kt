package org.idd.dia.application.port.out

import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member

interface InterviewScriptDbPort {
    fun isExists(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): Boolean
    fun save(interviewScript: InterviewScript): InterviewScript
    fun get(pk: InterviewScript.Pk): InterviewScript
    fun get(questionPk: InterviewQuestion.Pk, ownerPk: Member.Pk): InterviewScript
}
