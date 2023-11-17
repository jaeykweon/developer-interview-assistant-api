package org.idd.dia.adapter.db.mapper

import org.idd.dia.adapter.db.entity.InterviewQuestionVoiceEntity
import org.idd.dia.domain.InterviewQuestion
import org.idd.dia.domain.InterviewQuestionVoice
import org.springframework.stereotype.Component

@Component
class InterviewQuestionVoiceMapper {
    fun toDomainModel(entity: InterviewQuestionVoiceEntity): InterviewQuestionVoice {
        return InterviewQuestionVoice(
            pk = InterviewQuestionVoice.Pk(entity.pk),
            questionPk = InterviewQuestion.Pk(entity.questionPk),
            gender = entity.gender,
            filePath = InterviewQuestionVoice.FilePath(entity.filePath)
        )
    }

    fun toDbEntity(domainModel: InterviewQuestionVoice): InterviewQuestionVoiceEntity {
        return InterviewQuestionVoiceEntity(
            pk = domainModel.getPk(),
            questionPk = domainModel.getQuestionPk(),
            gender = domainModel.getGender(),
            filePath = domainModel.getFilePath()
        )
    }
}
