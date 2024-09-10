package org.idd.dia.application.port.usingcase.mapping

import org.idd.dia.domain.entity.InterviewQuestionCategoryEntity
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.idd.dia.domain.model.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InterviewQuestionBookmarkMappingDbPort {
    fun getMappings(
        memberPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): List<InterviewQuestionBookmarkMappingEntity>

    fun getMappings(
        memberPk: Member.Pk,
        interviewQuestionEntities: List<InterviewQuestionEntity>,
    ): List<InterviewQuestionBookmarkMappingEntity>

    fun getMappingsWithQuestion(
        memberPk: Member.Pk,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity>

    fun getMappingsWithQuestion(
        memberPk: Member.Pk,
        categoryEntities: Set<InterviewQuestionCategoryEntity>,
        pageable: Pageable,
    ): Page<InterviewQuestionBookmarkMappingEntity>

    fun getMappingsWithQuestion(pks: Iterable<Long>): List<InterviewQuestionBookmarkMappingEntity>

    fun addBookmark(
        memberPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): InterviewQuestionBookmarkMappingEntity

    fun removeBookmark(
        memberPk: Member.Pk,
        questionEntity: InterviewQuestionEntity,
    ): Long
}
