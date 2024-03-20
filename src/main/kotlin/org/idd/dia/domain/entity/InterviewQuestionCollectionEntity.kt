package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.idd.dia.domain.entity.mapping.InterviewQuestionCollectionMappingEntity
import org.idd.dia.domain.entity.mapping.getQuestionPks
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewQuestionCollection

@Table(name = "interview_question_collections")
@Entity
class InterviewQuestionCollectionEntity(
    pk: InterviewQuestionCollection.Pk,
    title: InterviewQuestionCollection.Title,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewQuestionCollection.Pk(pkValue)

    @Column(name = "title", nullable = false)
    val titleValue: String = title.value

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY)
    val questionMappings: Set<InterviewQuestionCollectionMappingEntity> = emptySet()

    val questionPks: List<InterviewQuestion.Pk>
        get() = questionMappings.getQuestionPks()

    val questionPkValues: List<Long>
        get() = questionMappings.getQuestionPks().map { it.value }

    companion object {
        @JvmStatic
        fun new(title: InterviewQuestionCollection.Title): InterviewQuestionCollectionEntity {
            return InterviewQuestionCollectionEntity(
                pk = InterviewQuestionCollection.Pk(0),
                title = title,
            )
        }
    }
}
