package me.manulorenzo.worldheritages_api.api.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.manulorenzo.worldheritages_api.api.data.model.HeritagesTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(HeritagesTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        with(config) {
            this.driverClassName = "org.postgresql.Driver"
            this.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
            this.maximumPoolSize = 3
            this.isAutoCommit = false
            this.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            this.validate()
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) { transaction { block() } }
}