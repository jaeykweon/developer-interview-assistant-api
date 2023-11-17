package org.idd.dia.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec

class InterviewScriptReferenceTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf

//    describe("InterviewScriptReference: 면접 대본 레퍼런스") {
//        val interviewScriptReference =
//            InterviewScriptReference(
//                pk = InterviewScriptReference.Pk(1L),
//                ownerPk = Member.Pk(1L),
//                questionPk = InterviewQuestion.Pk(1L),
//                scriptPk = InterviewScript.Pk(1L),
//                url = InterviewScriptReference.Url("https://jojoldu.tistory.com/734"),
//                clickCount = InterviewScriptReference.ClickCount(0L)
//            )
//
//        it("객체 생성 검증") {
//            assertSoftly(interviewScriptReference) {
//                getPk() shouldBe InterviewScriptReference.Pk(1L)
//                getOwnerPk() shouldBe Member.Pk(1L)
//                getQuestionPk() shouldBe InterviewQuestion.Pk(1L)
//                getScriptPk() shouldBe InterviewScript.Pk(1L)
//                getUrl() shouldBe InterviewScriptReference.Url("https://jojoldu.tistory.com/734")
//                getClickCount() shouldBe InterviewScriptReference.ClickCount(0L)
//            }
//        }
//
//        it("addClickCount(): 클릭 횟수를 1회 추가한다.") {
//            val before = interviewScriptReference.getClickCount()
//
//            interviewScriptReference.addClickCount()
//
//            interviewScriptReference.getClickCount().value shouldBe before.value + 1L
//        }
//    }
})
