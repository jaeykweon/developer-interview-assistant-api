package org.idd.dia.application.service.internal

import org.idd.dia.application.dto.InterviewScriptResponseV2
import org.idd.dia.application.port.usingcase.InterviewQuestionDbPort
import org.idd.dia.application.port.usingcase.InterviewScriptDbPort
import org.idd.dia.domain.entity.InterviewScriptEntity
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
class InterviewScriptInternalService(
    private val interviewQuestionDbPort: InterviewQuestionDbPort,
    private val interviewScriptDbPort: InterviewScriptDbPort,
) {
    fun getScripts(
        ownerPk: Member.Pk,
        questionPks: Collection<InterviewQuestion.Pk>,
    ): List<InterviewScriptResponseV2> {
        val questionEntities =
            interviewQuestionDbPort
                .getEntitiesWithOutRelations(questionPks)
        val scriptEntities: List<InterviewScriptEntity> =
            interviewScriptDbPort
                .getEntities(questionEntities, ownerPk)

        return scriptEntities.map {
            InterviewScriptResponseV2.from(it)
        }
    }
}
