package org.idd.dia.adapter.db.repository

import org.idd.dia.domain.entity.InterviewScriptReferenceEntity
import org.springframework.data.jpa.repository.JpaRepository

class InterviewScriptReferenceRepository(
    private val interviewScriptReferenceJpaRepository: InterviewScriptReferenceJpaRepository,
)

interface InterviewScriptReferenceJpaRepository : JpaRepository<InterviewScriptReferenceEntity, Long>
