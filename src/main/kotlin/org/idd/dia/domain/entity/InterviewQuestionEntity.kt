package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
import org.idd.dia.domain.model.InterviewQuestion

fun Iterable<InterviewQuestionEntity>.findPkMatches(pk: InterviewQuestion.Pk): InterviewQuestionEntity? {
    return this.find { it.getPk() == pk }
}

@Table(name = "interview_questions")
@Entity
class InterviewQuestionEntity(
    pk: InterviewQuestion.Pk,
    title: InterviewQuestion.Title,
    categoryMappingEntities: Set<InterviewQuestionCategoryMappingEntity>,
    voiceEntities: Set<InterviewQuestionVoiceEntity>,
) {
    @Id
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestion.Pk(pkValue)

    @Column(name = "title", nullable = false)
    val titleValue: String = title.value

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val categoryMappings: Set<InterviewQuestionCategoryMappingEntity> = categoryMappingEntities

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val voices: Set<InterviewQuestionVoiceEntity> = voiceEntities

    companion object {
        @JvmStatic
        fun new(title: InterviewQuestion.Title): InterviewQuestionEntity {
            return InterviewQuestionEntity(
                pk = InterviewQuestion.Pk(0),
                title = title,
                categoryMappingEntities = setOf(),
                voiceEntities = setOf(),
            )
        }
    }
}
