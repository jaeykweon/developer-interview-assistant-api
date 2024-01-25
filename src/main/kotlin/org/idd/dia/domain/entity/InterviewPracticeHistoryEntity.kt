package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.idd.dia.domain.model.InterviewPracticeHistory
import java.time.LocalDateTime

@Entity
@Table(name = "interview_practice_history")
class InterviewPracticeHistoryEntity(
    pk: InterviewPracticeHistory.Pk,
    owner: MemberEntity,
    type: InterviewPracticeHistory.Type,
    elapsedTime: InterviewPracticeHistory.ElapsedTime,
    filePath: InterviewPracticeHistory.FilePath,
    createdTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = InterviewPracticeHistory.Pk(pkValue)

    @ManyToOne
    @JoinColumn(referencedColumnName = "pk", nullable = false)
    val owner: MemberEntity = owner

    @Column(name = "type", nullable = false)
    val typeValue = type.name

    fun getType() = InterviewPracticeHistory.Type.valueOf(typeValue)

    @Column(name = "elapsed_time", nullable = false)
    val elapsedTimeValue = elapsedTime.value

    fun getElapsedTime() = InterviewPracticeHistory.ElapsedTime(elapsedTimeValue)

    @Column(name = "file_path", nullable = false)
    val filePath: String = filePath.value

    fun getFilePath() = InterviewPracticeHistory.FilePath(filePath)

    @Column(name = "created_time", nullable = false)
    private val createdTime: LocalDateTime = createdTime

    fun getCreatedTime() = createdTime
}