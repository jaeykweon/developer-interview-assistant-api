package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewScript
import org.idd.dia.domain.model.Member

interface InterviewScriptDbPort {
    fun isExists(
        questionEntity: InterviewQuestionEntity,
        ownerPk: Member.Pk,
    ): Boolean

    fun save(interviewScriptEntity: InterviewScriptEntity): InterviewScriptEntity

    fun getEntity(pk: InterviewScript.Pk): InterviewScriptEntity

    fun getEntity(
        questionEntity: InterviewQuestionEntity,
        ownerPk: Member.Pk,
    ): InterviewScriptEntity

    fun getEntity(questionEntity: InterviewQuestionEntity): InterviewScriptEntity

    fun getEntities(
        questionEntities: List<InterviewQuestionEntity>,
        ownerPk: Member.Pk,
    ): List<InterviewScriptEntity>
}
