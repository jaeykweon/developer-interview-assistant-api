package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewQuestionCollectionDetailViewModel
import org.idd.dia.application.dto.InterviewQuestionCollectionSimpleViewModels
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.port.usecase.InterviewQuestionCollectionServiceUseCase
import org.idd.dia.application.port.usecase.InterviewQuestionServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionCollectionDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewQuestionCollectionEntity
import org.idd.dia.domain.model.InterviewQuestionCollection
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewQuestionCollectionService(
    private val interviewQuestionCollectionDbPort: InterviewQuestionCollectionDbPort,
    private val interviewQuestionServiceUseCase: InterviewQuestionServiceUseCase,
    private val memberDbPort: MemberDbPort,
) : InterviewQuestionCollectionServiceUseCase {
    override fun getInterviewQuestionCollections(): InterviewQuestionCollectionSimpleViewModels {
        val collectionEntities: List<InterviewQuestionCollectionEntity> =
            interviewQuestionCollectionDbPort.getAllEntitiesWithQuestionMappings()

        return InterviewQuestionCollectionSimpleViewModels.of(collectionEntities)
    }

    override fun getInterviewQuestionCollectionForGuest(
        collectionPk: InterviewQuestionCollection.Pk,
    ): InterviewQuestionCollectionDetailViewModel {
        val collectionEntity: InterviewQuestionCollectionEntity =
            interviewQuestionCollectionDbPort.getEntityWithQuestionMappings(pk = collectionPk)
                ?: throw NotFoundException("Collection not found")
        val questionResponses = interviewQuestionServiceUseCase.getQuestionsWithoutBookmark(collectionEntity.questionPks)

        return InterviewQuestionCollectionDetailViewModel.of(collectionEntity, questionResponses)
    }

    override fun getInterviewQuestionCollectionForMember(
        memberPk: Member.Pk,
        collectionPk: InterviewQuestionCollection.Pk,
    ): InterviewQuestionCollectionDetailViewModel {
        val collectionEntity =
            interviewQuestionCollectionDbPort.getEntityWithQuestionMappings(pk = collectionPk)
                ?: throw NotFoundException("Collection not found")

        val memberEntity = memberDbPort.getEntity(memberPk)
        val questionResponses: List<InterviewQuestionResponse> =
            interviewQuestionServiceUseCase
                .getQuestionsWithBookmark(memberEntity, collectionEntity.questionPks)

        return InterviewQuestionCollectionDetailViewModel.of(collectionEntity, questionResponses)
    }
}
