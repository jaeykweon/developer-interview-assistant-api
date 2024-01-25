package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.idd.dia.domain.entity.mapping.InterviewQuestionCategoryMappingEntity
import org.idd.dia.domain.model.InterviewQuestion

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

    fun getKorTitle() = InterviewQuestion.Title(korTitleValue)

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    val categories: Set<InterviewQuestionCategoryMappingEntity> = categories

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    val voices: Set<InterviewQuestionVoiceEntity> = voices
}
