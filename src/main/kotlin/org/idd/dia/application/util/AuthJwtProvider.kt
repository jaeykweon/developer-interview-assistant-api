package org.idd.dia.application.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.idd.dia.domain.DiaUserPk
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

interface AuthProvider {
    fun createToken(diaUserPk: DiaUserPk, createTime: Date = Date()): AuthToken
    fun verify(token: AuthToken): AccessTokenContent
    fun extractTokenInfo(decodedJwt: DecodedJWT): AccessTokenContent
}

@Component
class AuthJwtProvider(
    @Value("\${jwt.auth.secret_key}")
    private val jwtSecretKey: String,
    @Value("\${jwt.auth.issuer}")
    private val issuer: String,
    @Value("\${jwt.auth.subject}")
    private val subject: String,
    @Value("\${jwt.auth.expire_time}")
    private val expireTime: String
) : AuthProvider {

    private val algorithm: Algorithm = Algorithm.HMAC256(jwtSecretKey)

    override fun createToken(diaUserPk: DiaUserPk, createTime: Date): AuthToken {
        val expireTime = Date(createTime.time + expireTime.toLong())

        val token = JWT.create()
            .withIssuer(issuer)
            .withSubject(subject)
            .withIssuedAt(createTime)
            .withExpiresAt(expireTime)
            .withClaim(DIA_USER_PK, diaUserPk.value)
            .sign(algorithm)

        return AuthJwt(token)
    }

    override fun verify(token: AuthToken): AccessTokenContent {
        val decodedJwt = try {
            JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token.getTokenString())
        } catch (e: JWTVerificationException) {
            throw IllegalArgumentException(e)
        }

        return extractTokenInfo(decodedJwt)
    }

    override fun extractTokenInfo(decodedJwt: DecodedJWT): AccessTokenContent {
        val diaUserPk = DiaUserPk(
            decodedJwt.getClaim(DIA_USER_PK).asLong()
        )
        return AccessTokenContent(
            diaUserPk = diaUserPk
        )
    }

    companion object {
        const val DIA_USER_PK = "DIA_USER_PK"
    }
}
