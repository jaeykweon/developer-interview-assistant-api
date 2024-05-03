package org.idd.dia.domain.model

import org.idd.dia.domain.BadRequestException

interface MemberToken {
    @JvmInline
    value class Pk(
        val value: Long,
    )

    @JvmInline
    value class AccessToken(
        val value: String,
    ) {
        init {
            require(value.isNotEmpty()) { throw BadRequestException("access token is empty") }
        }
    }

    @JvmInline
    value class RefreshToken(
        val value: String,
    ) {
        init {
            require(value.isNotEmpty()) { throw BadRequestException("refresh token is empty") }
        }
    }

    @JvmInline
    value class UserAgent(
        val value: String,
    )
}
