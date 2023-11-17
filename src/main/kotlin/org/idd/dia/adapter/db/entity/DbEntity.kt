package org.idd.dia.adapter.db.entity

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

// todo: createdAt, updatedAt이 필요없는 테이블도 있을텐데, 그래도 여기에 쓰는 것이 맞나
// todo: pk를 모든 테이블에서 유일하게 하게 할 수 있나?
@MappedSuperclass
abstract class DbEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val pk: Long
)
