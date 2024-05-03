package org.idd.dia.domain.model

import java.util.function.Function

enum class Gender {
    MALE,
    FEMALE,
    ;

    companion object {
        fun from(string: String): Gender {
            return valueOf(string)
        }
    }
}

enum class Language {
    KOREAN,
    ENGLISH,
    JAPANESE,
    ;

    companion object {
        fun from(string: String): Language {
            return valueOf(string)
        }
    }
}

data class CustomPage<T>(
    val pageData: List<T>,
    val pageSize: Int,
    val pageNumber: Int,
    val totalPages: Int,
) {
    fun <U> map(converter: Function<in T, out U>): CustomPage<U> {
        val convertedData = pageData.map { converter.apply(it) }
        return CustomPage(
            convertedData,
            pageSize,
            pageNumber,
            totalPages,
        )
    }
}

data class CustomScroll<T>(
    val scrollData: List<T>,
    val next: Boolean,
) {
    fun <U> map(converter: Function<in T, out U>): CustomScroll<U> {
        val convertedData =
            scrollData.map {
                converter.apply(it)
            }
        return CustomScroll(
            convertedData,
            next,
        )
    }
}
