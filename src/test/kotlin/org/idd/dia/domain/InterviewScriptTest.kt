package org.idd.dia.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec

/**
 * @see InterviewScript
 */
class InterviewScriptTest :
    DescribeSpec({

        isolationMode = IsolationMode.InstancePerLeaf

//    describe("InterviewScript: 면접 대본") {
//        val createdTime = LocalDateTime.now()
//
//        val interviewScript = InterviewScript(
//            pk = InterviewScript.Pk(1L),
//            ownerPk = Member.Pk(1L),
//            questionPk = InterviewQuestion.Pk(1L),
//            content = InterviewScript.Content("면접 대본 내용"),
//            createdTime = createdTime,
//            lastModifiedTime = createdTime,
//            lastReadTime = createdTime
//        )
//
//        it("객체 생성 검증") {
//            assertSoftly(interviewScript) {
//                getPk() shouldBe InterviewScript.Pk(1L)
//                getOwnerPk() shouldBe Member.Pk(1L)
//                getQuestionPk() shouldBe InterviewQuestion.Pk(1L)
//                readContent(Member.Pk(1L), createdTime) shouldBe
//                    InterviewScript.Content("면접 대본 내용")
//                getCreatedTime() shouldBe createdTime
//                getLastModifiedTime() shouldBe createdTime
//                getLastReadTime() shouldBe createdTime
//            }
//        }
//
//        it("readContent(requestMemberPk, readTime): 면접 대본 내용 읽기") {
//            // 면접 대본 내용은 주인만 볼 수 있으며, 내용 확인시 마지막으로 읽은 시간이 업데이트 된다.
//            val readTime = LocalDateTime.now().plusHours(1)
//
//            interviewScript.readContent(Member.Pk(1L), readTime) shouldBe
//                InterviewScript.Content("면접 대본 내용")
//
//            interviewScript.getLastReadTime() shouldBe readTime
//
//            // 주인이 아니면 내용을 볼 수 없다
//            shouldThrowExactly<IllegalArgumentException> {
//                interviewScript.readContent(Member.Pk(2L), readTime)
//            }
//        }
//    }
    })
