package org.idd.dia.application.port.usecase

import org.idd.dia.application.dto.InterviewQuestionCollectionDetailViewModel
import org.idd.dia.application.dto.InterviewQuestionCollectionSimpleViewModels
import org.idd.dia.domain.model.InterviewQuestionCollection
import org.idd.dia.domain.model.Member

interface InterviewQuestionCollectionServiceUseCase {
    fun getInterviewQuestionCollections(): InterviewQuestionCollectionSimpleViewModels

    fun getInterviewQuestionCollectionForGuest(collectionPk: InterviewQuestionCollection.Pk): InterviewQuestionCollectionDetailViewModel

    fun getInterviewQuestionCollectionForMember(
        memberPk: Member.Pk,
        collectionPk: InterviewQuestionCollection.Pk,
    ): InterviewQuestionCollectionDetailViewModel
}
