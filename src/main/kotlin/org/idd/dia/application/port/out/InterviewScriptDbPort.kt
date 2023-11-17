package org.idd.dia.application.port.out

import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript

interface InterviewScriptDbPort {
    fun save(interviewScript: InterviewScript): InterviewScript
    fun get(pk: InterviewScript.Pk): InterviewScript
    fun get(questionPk: InterviewQuestion.Pk): InterviewScript
}
