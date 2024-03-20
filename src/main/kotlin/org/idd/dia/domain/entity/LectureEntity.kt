package org.idd.dia.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.idd.dia.domain.model.Lecture

@Table(name = "lectures")
@Entity
class LectureEntity(
    pk: Lecture.Pk,
    url: Lecture.Url,
    price: Lecture.Price,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    val pkValue: Long = pk.value

    fun getPk() = Lecture.Pk(pkValue)

    @Column(name = "url", nullable = false)
    val urlValue: String = url.value

    @Column(name = "price", nullable = false)
    val priceValue: Int = price.value
}
