package org.idd.dia.domain.entity

import org.idd.dia.domain.model.InterviewQuestion
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "interview_question")
@Entity
class InterviewQuestionEntity(
    pk: InterviewQuestion.Pk,
    val title: InterviewQuestion.Title
) : DbEntity(pk = pk.value)
