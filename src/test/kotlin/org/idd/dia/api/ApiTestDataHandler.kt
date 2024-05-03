package org.idd.dia.api

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.Table
import jakarta.persistence.metamodel.ManagedType
import jakarta.persistence.metamodel.Metamodel
import jakarta.transaction.Transactional
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.stereotype.Component
import java.io.IOException
import java.sql.SQLException
import java.util.Locale
import javax.sql.DataSource
import kotlin.reflect.full.findAnnotation

/**
 * https://github.com/the-serious-programmer/truncate-test-tables
 */
@Profile("test")
@Component
class ApiTestDataHandler(
    private val entityManager: EntityManager,
    private val dataSource: DataSource,
) : InitializingBean {
    private val initDataSqlPath = "classpath:TestData.sql"
    private var tableNames = listOf<String>()
    private val toSnakeRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

    override fun afterPropertiesSet() {
        tableNames = getManagedTableNames()
    }

    /**
     * initDataSqlPath에 정의된 SQL 파일을 실행하여 테스트 데이터를 초기화합니다.
     */
    fun setUp() {
        try {
            dataSource.connection.use { connection ->
                val resources: Array<Resource> =
                    PathMatchingResourcePatternResolver().getResources(initDataSqlPath)
                for (resource in resources) {
                    ScriptUtils.executeSqlScript(connection, resource)
                }
            }
        } catch (ex: SQLException) {
            throw RuntimeException("Failed to execute SQL script", ex)
        } catch (ex: IOException) {
            throw RuntimeException("Failed to execute SQL script", ex)
        }
    }

    /**
     * 모든 테이블을 삭제 합니다.
     */
    @Transactional
    fun truncate() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE")
            .executeUpdate()

        tableNames.forEach {
            entityManager
                .createNativeQuery("TRUNCATE TABLE $it RESTART IDENTITY")
                .executeUpdate()
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE")
            .executeUpdate()
    }

    // Resolve table names from all managed types that have either a @Table or @Entity annotation defined
    private fun getManagedTableNames(): List<String> {
        val metaModel: Metamodel = entityManager.metamodel
        return metaModel.managedTypes
            .filter {
                val kotlinClass = it.javaType.kotlin
                kotlinClass.findAnnotation<Table>() != null ||
                    kotlinClass.findAnnotation<Entity>() != null
            }
            .map {
                val annotationName = getAnnotationName(it)
                getTableName(annotationName, it.javaType.simpleName)
            }
    }

    // Get name from managed type
    // @Table is optional, @Entity is a mandatory annotation for every defined entity (hence the !!)
    private fun getAnnotationName(managedType: ManagedType<*>): String {
        val kotlinClass = managedType.javaType.kotlin
        return kotlinClass.findAnnotation<Table>()?.name
            ?: kotlinClass.findAnnotation<Entity>()!!.name
    }

    // Either get the name defined in the annotation and otherwise convert the java type to the default naming strategy (snake case)
    private fun getTableName(
        annotationName: String,
        javaTypeName: String,
    ): String {
        return if (annotationName == "") {
            camelToSnakeCase(javaTypeName)
        } else {
            annotationName
        }
    }

    private fun camelToSnakeCase(camelString: String): String {
        return toSnakeRegex.replace(camelString) {
            "_${it.value}"
        }.lowercase(Locale.getDefault())
    }
}
