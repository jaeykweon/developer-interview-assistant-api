package org.idd.dia.adapter.db.mapper

import org.idd.dia.adapter.db.entity.InterviewScriptEntity
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewScript
import org.idd.dia.domain.Member
import org.springframework.stereotype.Component

@Component
class InterviewScriptMapper {

    fun toDomain(entity: InterviewScriptEntity): InterviewScript {
        return InterviewScript(
            pk = InterviewScript.Pk(entity.pk),
            ownerPk = Member.Pk(entity.ownerPk),
            questionPk = InterviewQuestion.Pk(entity.questionPk),
            content = entity.content,
            createdTime = entity.createdTime,
            lastModifiedTime = entity.lastModifiedTime,
            lastReadTime = entity.lastReadTime
        )
    }

    fun toEntity(domain: InterviewScript): InterviewScriptEntity {
        return InterviewScriptEntity(
            pk = domain.getPk(),
            ownerPk = domain.getOwnerPk(),
            questionPk = domain.getQuestionPk(),
            content = domain.getContent(),
            createdTime = domain.getCreatedTime(),
            lastModifiedTime = domain.getLastModifiedTime(),
            lastReadTime = domain.getLastReadTime()
        )
    }
}
