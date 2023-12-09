package org.idd.dia.domain.entity

import org.idd.dia.domain.model.Lecture
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "lecture")
@Entity
class LectureEntity(
    pk: Lecture.Pk,
    url: Lecture.Url,
    price: Lecture.Price
) : DbEntity(pk = pk.value) {

    val url: String = url.value

    val price: Int = price.value
}
