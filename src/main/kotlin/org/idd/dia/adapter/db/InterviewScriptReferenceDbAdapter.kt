package org.idd.dia.adapter.db

import org.idd.dia.application.dto.MostQuotedReferencesOfQuestion
import org.idd.dia.application.port.out.InterviewScriptReferenceDbPort
import org.idd.dia.domain.model.InterviewQuestion
import org.idd.dia.domain.model.InterviewScriptReference
import org.idd.dia.domain.model.Member
import org.springframework.stereotype.Component

@Component
class InterviewScriptReferenceDbAdapter : InterviewScriptReferenceDbPort {
    override fun getReference(pk: InterviewScriptReference.Pk): InterviewScriptReference {
        TODO("Not yet implemented")
    }

    override fun getReference(questionPk: InterviewQuestion.Pk): InterviewScriptReference {
        TODO("Not yet implemented")
    }

    override fun getMostQuotedReference(questionPk: InterviewQuestion.Pk): MostQuotedReferencesOfQuestion {
        TODO("Not yet implemented")
    }

    override fun save(interviewScriptReference: InterviewScriptReference): InterviewScriptReference {
        TODO("Not yet implemented")
    }

    override fun delete(pk: InterviewScriptReference.Pk, memberPk: Member.Pk) {
        TODO("Not yet implemented")
    }
}
