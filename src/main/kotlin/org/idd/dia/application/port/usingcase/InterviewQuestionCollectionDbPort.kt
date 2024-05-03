package org.idd.dia.application.port.usingcase

import org.idd.dia.domain.entity.InterviewQuestionCollectionEntity
import org.idd.dia.domain.model.InterviewQuestionCollection

interface InterviewQuestionCollectionDbPort {
    fun getAllEntitiesWithQuestionMappings(): List<InterviewQuestionCollectionEntity>

    fun getEntityWithQuestionMappings(pk: InterviewQuestionCollection.Pk): InterviewQuestionCollectionEntity
}
