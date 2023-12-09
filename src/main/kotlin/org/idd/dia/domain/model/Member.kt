package org.idd.dia.domain.model

class Member(
    private val pk: Pk,
    private val githubId: GithubId
) {

    @JvmInline
    value class Pk(
        val value: Long = 0L
    )

    @JvmInline
    value class GithubId(
        val value: String
    )

    enum class Role {
        FRONT,
        BACK,
        FULLSTACK
    }
}
