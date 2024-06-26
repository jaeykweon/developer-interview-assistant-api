package org.idd.dia.domain.model

interface Member {
    @JvmInline
    value class Pk(
        val value: Long = 0L,
    )

    @JvmInline
    value class OauthId(
        val value: String,
    )

    @JvmInline
    value class Nickname(
        val value: String,
    )

    @JvmInline
    value class ImageUrl(
        val value: String,
    )

    enum class OauthProvider {
        GITHUB,
    }

    enum class Role {
        FRONT,
        BACK,
        FULLSTACK,
    }
}
