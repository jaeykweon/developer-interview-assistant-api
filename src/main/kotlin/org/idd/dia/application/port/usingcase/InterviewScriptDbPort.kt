package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.model.InterviewScript

interface InterviewScriptDbPort {
    fun isExists(
        questionEntity: InterviewQuestionEntity,
        ownerEntity: MemberEntity,
    ): Boolean

    fun save(interviewScriptEntity: InterviewScriptEntity): InterviewScriptEntity

    fun getByPk(pk: InterviewScript.Pk): InterviewScriptEntity

    fun getByPkAndOwnerPk(
        questionEntity: InterviewQuestionEntity,
        ownerEntity: MemberEntity,
    ): InterviewScriptEntity

    fun getByQuestionPk(questionEntity: InterviewQuestionEntity): InterviewScriptEntity

    fun getAllByQuestionsOfMember(
        questionEntities: List<InterviewQuestionEntity>,
        ownerEntity: MemberEntity,
    ): List<InterviewScriptEntity>
}
