package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
import org.idd.dia.domain.model.InterviewQuestion
import java.time.LocalDateTime

fun Iterable<InterviewQuestionEntity>.findPkMatches(pk: InterviewQuestion.Pk): InterviewQuestionEntity? {
    return this.find { it.getPk() == pk }
}

fun Iterable<InterviewQuestionEntity>.getPks() = this.map { it.getPk() }

/**
 * 이 함수를 Entity 안에 정의하면, pk만 가져오려는 상황에 n+1 문제가 발생할 수 있음
 * 프록시 객체에서 id 속성이 붙은 것을 요청하는 것 이외에는 모두 초기화가 되어버리기 때문
 */
fun InterviewQuestionEntity.getPk() = InterviewQuestion.Pk(this.pkValue)

@Table(name = "interview_questions")
@Entity
class InterviewQuestionEntity(
    pk: InterviewQuestion.Pk,
    title: InterviewQuestion.Title,
    categoryMappingEntities: Set<InterviewQuestionCategoryMappingEntity>,
    voiceEntities: Set<InterviewQuestionVoiceEntity>,
    createdTime: LocalDateTime,
) : CommonEntity(createdTime) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    @Column(name = "title", nullable = false)
    val titleValue: String = title.value

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val categoryMappings: Set<InterviewQuestionCategoryMappingEntity> = categoryMappingEntities

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val voices: Set<InterviewQuestionVoiceEntity> = voiceEntities

    companion object {
        @JvmStatic
        fun new(
            title: InterviewQuestion.Title,
            time: LocalDateTime = LocalDateTime.now(),
        ): InterviewQuestionEntity {
            return InterviewQuestionEntity(
                pk = InterviewQuestion.Pk(0),
                title = title,
                categoryMappingEntities = setOf(),
                voiceEntities = setOf(),
                createdTime = time,
            )
        }
    }
}
