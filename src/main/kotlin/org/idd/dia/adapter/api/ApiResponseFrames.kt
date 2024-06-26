package org.idd.dia.adapter.api

import org.idd.dia.domain.model.CustomPage
import org.idd.dia.domain.model.CustomScroll
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus

fun <T> Page<T?>.dropNull(): PageImpl<T> =
    PageImpl(
        this.content.filterNotNull(),
        this.pageable,
        this.totalElements,
    )

fun <T> Page<T>.toCustomPage(): CustomPage<T> =
    CustomPage(
        pageData = this.content,
        pageSize = this.size,
        pageNumber = this.number,
        totalPages = this.totalPages,
    )

fun <T> Slice<T>.toCustomScroll(): CustomScroll<T> =
    CustomScroll(
        scrollData = this.content,
        next = this.hasNext(),
    )

data class ApiResponse<T>(
    val data: T?,
    val status: Int,
    val detail: String?,
) {
    companion object {
        @JvmStatic
        fun <T> ok(
            data: T?,
            detail: String? = null,
        ): ApiResponse<T> =
            ApiResponse(
                data = data,
                status = HttpStatus.OK.value(),
                detail = detail,
            )

        @JvmStatic
        fun badRequest(detail: String?): ApiResponse<Nothing> =
            ApiResponse(
                data = null,
                status = HttpStatus.BAD_REQUEST.value(),
                detail = detail,
            )

        @JvmStatic
        fun unauthorized(detail: String?): ApiResponse<Nothing> =
            ApiResponse(
                data = null,
                status = HttpStatus.UNAUTHORIZED.value(),
                detail = detail,
            )

        @JvmStatic
        fun notFound(detail: String?): ApiResponse<Nothing> =
            ApiResponse(
                data = null,
                status = HttpStatus.NOT_FOUND.value(),
                detail = detail,
            )

        @JvmStatic
        fun conflict(detail: String?): ApiResponse<Nothing> =
            ApiResponse(
                data = null,
                status = HttpStatus.CONFLICT.value(),
                detail = detail,
            )

        @JvmStatic
        fun internalServerError(detail: String?): ApiResponse<Nothing> =
            ApiResponse(
                data = null,
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                detail = detail,
            )
    }
}
