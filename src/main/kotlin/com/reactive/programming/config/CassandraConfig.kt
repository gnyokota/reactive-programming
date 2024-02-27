package com.reactive.programming.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories
import java.util.*


@Configuration
@EnableCassandraRepositories
class CassandraConfiguration : AbstractCassandraConfiguration() {
    @Value("\${spring.cassandra.contactpoints}")
    private val contactPoint: String? = null

    @Value("\${spring.cassandra.port}")
    private val port = 0

    @Value("\${spring.data.cassandra.keyspace-name}")
    private val keyspaceName: String? = null

    @Value("\${spring.cassandra.basepackages}")
    private val basePackages: String? = null

    override fun getKeyspaceName(): String {
        return keyspaceName!!
    }

    override fun getPort(): Int {
        return port
    }

    override fun getContactPoints(): String {
        return contactPoint!!
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    override fun getEntityBasePackages(): Array<String?> {
        return arrayOf(basePackages)
    }

    override fun cassandraSession(): CqlSessionFactoryBean {
        val cqlSessionFactoryBean = super.cassandraSession()
        return cqlSessionFactoryBean
    }

    override fun getKeyspaceCreations(): List<CreateKeyspaceSpecification> {
        val specification =
            CreateKeyspaceSpecification.createKeyspace(keyspaceName!!)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication()
        return java.util.List.of(specification)
    }

    override fun getKeyspaceDrops(): List<DropKeyspaceSpecification> {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(keyspaceName!!))
    }
}