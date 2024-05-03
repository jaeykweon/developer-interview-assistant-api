package org.idd.dia.domain

/** 400 Bad Request */
class BadRequestException(
    override val message: String,
) : RuntimeException()

/** 401 Unauthorized */
class UnAuthorizedException(
    override val message: String,
) : RuntimeException()

/** 403 Forbidden */
class ForbiddenException(
    override val message: String,
) : RuntimeException()

/** 404 Not Found */
class NotFoundException(
    override val message: String,
) : RuntimeException()

/** 409 Conflict */
class ConflictException(
    override val message: String,
) : RuntimeException()

/** 500 Internal Server Error */
class InternalServerErrorException(
    override val message: String,
) : RuntimeException()

/** 503 Service Unavailable */
class ServiceUnavailableException(
    override val message: String,
) : RuntimeException()
