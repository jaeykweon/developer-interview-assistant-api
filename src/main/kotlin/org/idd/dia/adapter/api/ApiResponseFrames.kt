package org.idd.dia.adapter.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus

data class CustomPage<T> (
    val pageData: List<T>,
    val pageSize: Int,
    val pageNumber: Int,
    val totalPages: Int,
)

fun <T> Page<T>.toCustomPage(): CustomPage<T> {
    return CustomPage(
        pageData = this.content,
        pageSize = this.size,
        pageNumber = this.number,
        totalPages = this.totalPages
    )
}

data class CustomScroll<T>(
    val scrollData: List<T>,
    val next: Boolean
)

fun <T> Slice<T>.toCustomScroll(): CustomScroll<T> {
    return CustomScroll(
        scrollData = this.content,
        next = this.hasNext()
    )
}

data class ApiResponse <T> (
    val data: T?,
    val status: Int,
    val detail: String?
) {
    companion object {
        @JvmStatic
        fun <T> ok(data: T, detail: String? = null): ApiResponse<T> {
            return ApiResponse(
                data = data,
                status = HttpStatus.OK.value(),
                detail = detail
            )
        }

        @JvmStatic
        fun <T> badRequest(detail: String?): ApiResponse<Nothing> {
            return ApiResponse(
                data = null,
                status = HttpStatus.BAD_REQUEST.value(),
                detail = detail
            )
        }

        @JvmStatic
        fun <T> unauthorized(detail: String?): ApiResponse<Nothing> {
            return ApiResponse(
                data = null,
                status = HttpStatus.UNAUTHORIZED.value(),
                detail = detail
            )
        }

        @JvmStatic
        fun <T> notFound(detail: String?): ApiResponse<Nothing> {
            return ApiResponse(
                data = null,
                status = HttpStatus.NOT_FOUND.value(),
                detail = detail
            )
        }

        @JvmStatic
        fun <T> internalServerError(detail: String?): ApiResponse<Nothing> {
            return ApiResponse(
                data = null,
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                detail = detail
            )
        }
    }
}
