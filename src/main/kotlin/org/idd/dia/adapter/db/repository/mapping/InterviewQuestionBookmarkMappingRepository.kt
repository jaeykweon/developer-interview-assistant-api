package org.idd.dia.adapter.db.repository.mapping

import org.idd.dia.domain.ConflictException
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.entity.InterviewQuestionEntity
import org.idd.dia.domain.entity.MemberEntity
import org.idd.dia.domain.entity.mapping.InterviewQuestionBookmarkMappingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InterviewQuestionBookmarkMappingRepository(
    private val interviewQuestionBookmarkMappingJpaRepository: InterviewQuestionBookmarkMappingJpaRepository,
) {
    fun getMappingsOfQuestion(
        ownerEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Iterable<InterviewQuestionBookmarkMappingEntity> {
        return getMappingsOfQuestions(ownerEntity, listOf(questionEntity))
    }

    fun getMappingsOfQuestions(
        ownerEntity: MemberEntity,
        interviewQuestionEntities: List<InterviewQuestionEntity>,
    ): Iterable<InterviewQuestionBookmarkMappingEntity> {
        return interviewQuestionBookmarkMappingJpaRepository
            .findAllByOwnerAndQuestionIn(ownerEntity, interviewQuestionEntities)
    }

    fun addBookmark(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Long {
        if (isBookmarkExist(memberEntity, questionEntity)) {
            throw ConflictException("Bookmark already exists")
        }
        return interviewQuestionBookmarkMappingJpaRepository.save(
            InterviewQuestionBookmarkMappingEntity.new(
                question = questionEntity,
                owner = memberEntity,
                createdTime = LocalDateTime.now(),
            ),
        ).pkValue
    }

    fun removeBookmark(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Long {
        if (!isBookmarkExist(memberEntity, questionEntity)) {
            // todo: 이것은 bad request, not found, conflict 중 어떤 예외인가
            throw NotFoundException("Bookmark already exists")
        }
        val old = interviewQuestionBookmarkMappingJpaRepository.findByOwnerAndQuestion(memberEntity, questionEntity)!!
        interviewQuestionBookmarkMappingJpaRepository.deleteById(old.pkValue)
        return old.pkValue
    }

    private fun isBookmarkExist(
        memberEntity: MemberEntity,
        questionEntity: InterviewQuestionEntity,
    ): Boolean {
        return interviewQuestionBookmarkMappingJpaRepository.existsByQuestionAndOwner(questionEntity, memberEntity)
    }
}

interface InterviewQuestionBookmarkMappingJpaRepository : JpaRepository<InterviewQuestionBookmarkMappingEntity, Long> {
    fun findByOwnerAndQuestion(
        owner: MemberEntity,
        question: InterviewQuestionEntity,
    ): InterviewQuestionBookmarkMappingEntity?

    fun findAllByOwnerAndQuestionIn(
        owner: MemberEntity,
        questions: List<InterviewQuestionEntity>,
    ): Set<InterviewQuestionBookmarkMappingEntity>

    fun existsByQuestionAndOwner(
        question: InterviewQuestionEntity,
        owner: MemberEntity,
    ): Boolean
}
