package org.idd.dia.application.service

import jakarta.transaction.Transactional
import org.idd.dia.application.dto.InterviewScriptFormResponse
import org.idd.dia.application.dto.InterviewQuestionCollectionDetailViewModel
import org.idd.dia.application.dto.InterviewQuestionCollectionSimpleViewModels
import org.idd.dia.application.dto.InterviewQuestionResponse
import org.idd.dia.application.dto.InterviewScriptResponseV2
import org.idd.dia.application.port.usecase.InterviewQuestionCollectionServiceUseCase
import org.idd.dia.application.port.usingcase.InterviewQuestionCollectionDbPort
import org.idd.dia.application.port.usingcase.MemberDbPort
import org.idd.dia.application.service.internal.InterviewQuestionInternalService
import org.idd.dia.application.service.internal.InterviewScriptInternalService
import org.idd.dia.domain.entity.InterviewQuestionCollectionEntity
import org.idd.dia.domain.model.InterviewQuestionCollection
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Service

@Service
@Transactional
class InterviewQuestionCollectionService(
    private val interviewQuestionCollectionDbPort: InterviewQuestionCollectionDbPort,
    private val memberDbPort: MemberDbPort,
    private val interviewScriptInternalService: InterviewScriptInternalService,
    private val interviewQuestionInternalService: InterviewQuestionInternalService,
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

        val questionResponses: List<InterviewQuestionResponse> =
            interviewQuestionInternalService
                .getQuestionsWithoutBookmark(collectionEntity.questionPks)

        val scriptForms = InterviewScriptFormResponse.multiOfGuest(questionResponses)

        return InterviewQuestionCollectionDetailViewModel.of(collectionEntity, scriptForms)
    }

    override fun getInterviewQuestionCollectionForMember(
        memberPk: Member.Pk,
        collectionPk: InterviewQuestionCollection.Pk,
    ): InterviewQuestionCollectionDetailViewModel {
        val memberEntity = memberDbPort.getEntity(pk = memberPk)

        val collectionEntity: InterviewQuestionCollectionEntity =
            interviewQuestionCollectionDbPort.getEntityWithQuestionMappings(pk = collectionPk)

        val questionResponses: List<InterviewQuestionResponse> =
            interviewQuestionInternalService
                .getQuestionsWithBookmark(memberEntity, collectionEntity.questionPks)

        val scriptResponses: List<InterviewScriptResponseV2> =
            interviewScriptInternalService
                .getScripts(memberEntity, collectionEntity.questionPks)

        val scriptForms: List<InterviewScriptFormResponse> =
            InterviewScriptFormResponse
                .multiOfMember(questionResponses, scriptResponses)

        return InterviewQuestionCollectionDetailViewModel.of(collectionEntity, scriptForms)
    }
}
