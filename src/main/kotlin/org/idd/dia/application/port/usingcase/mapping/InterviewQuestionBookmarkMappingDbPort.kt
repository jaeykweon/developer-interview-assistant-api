package org.idd.dia.application.port.usingcase.mapping

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionBookmarkMappingDbPort {
    fun getMappings(
        ownerEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): List<InterviewQuestionBookmarkMappingEntity>

    fun getMappings(
        ownerEntity: MemberEntity,
        interviewQuestionEntities: List<InterviewQuestionEntity>,
    ): List<InterviewQuestionBookmarkMappingEntity>

    fun getMappingsWithQuestion(
        ownerEntity: MemberEntity,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity>

    fun getMappingsWithQuestion(
        ownerEntity: MemberEntity,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity>

    fun getMappingsWithQuestion(pks: Iterable<Long>): List<InterviewQuestionBookmarkMappingEntity>

    fun addBookmark(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): InterviewQuestionBookmarkMappingEntity

    fun removeBookmark(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Long
}
