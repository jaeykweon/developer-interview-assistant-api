package org.idd.dia.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec

/**
 * @see InterviewQuestion
 */
class InterviewQuestionTest :
    DescribeSpec({

        isolationMode = IsolationMode.InstancePerLeaf

//    describe("InterviewQuestion: 면접 질문") {
//
//        val interviewQuestion = InterviewQuestion(
//            pk = InterviewQuestion.Pk(1L),
//            title = InterviewQuestion.Title("인증과 인가의 차이를 설명해보세요")
//        )
//
//        it("객체 생성 검증") {
//            assertSoftly(interviewQuestion) {
//                getPk() shouldBe InterviewQuestion.Pk(1L)
//                getTitle() shouldBe InterviewQuestion.Title("인증과 인가의 차이를 설명해보세요")
//            }
//        }
//    }
    })
