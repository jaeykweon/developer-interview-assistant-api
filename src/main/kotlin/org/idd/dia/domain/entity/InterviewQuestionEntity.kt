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

@Table(name = "interview_question")
@Entity
class InterviewQuestionEntity(
    pk: InterviewQuestion.Pk,
    korTitle: InterviewQuestion.Title,
    categories: Set<InterviewQuestionCategoryMappingEntity>,
    voices: Set<InterviewQuestionVoiceEntity>,
) {
    @Id
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestion.Pk(pkValue)

    @Column(name = "kor_title", nullable = false)
    val korTitleValue = korTitle.value

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val categories: Set<InterviewQuestionCategoryMappingEntity> = categories

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val voices: Set<InterviewQuestionVoiceEntity> = voices
}
