package org.idd.dia.adapter.db.mapper

import org.idd.dia.adapter.db.entity.InterviewQuestionEntity
import org.idd.dia.domain.InterviewQuestion
import org.springframework.stereotype.Component

@Component
class InterviewQuestionMapper {

    fun toDomainModel(
        entity: InterviewQuestionEntity,
    ): InterviewQuestion {
        return InterviewQuestion(
            pk = InterviewQuestion.Pk(entity.pk),
            title = entity.title
        )
    }

    fun toDbEntity(domainModel: InterviewQuestion): InterviewQuestionEntity {
        return InterviewQuestionEntity(
            pk = domainModel.getPk(),
            title = domainModel.getTitle()
        )
    }
}
