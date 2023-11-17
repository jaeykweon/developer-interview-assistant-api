package org.idd.dia.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec

class InterviewQuestionVoiceTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf

//    describe("InterviewQuestionVoice: 면접 질문 음성") {
//
//        val questionVoice =
//            InterviewQuestionVoice(
//                pk = InterviewQuestionVoice.Pk(1L),
//                questionPk = InterviewQuestion.Pk(1L),
//                gender = InterviewQuestionVoice.Gender.MALE,
//                url = InterviewQuestionVoice.Url("https://s3")
//            )
//
//        it("객체 생성 검증") {
//            assertSoftly(questionVoice) {
//                getPk() shouldBe InterviewQuestionVoice.Pk(1L)
//                getQuestionPk() shouldBe InterviewQuestion.Pk(1L)
//                getGender() shouldBe InterviewQuestionVoice.Gender.MALE
//                getUrl() shouldBe InterviewQuestionVoice.Url("https://s3")
//            }
//        }
//    }
})
