package io.github.emikaelsilveira.application.config.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class DatabaseConfig {

    companion object {
        private const val JDBC_URL = "jdbc:postgresql://localhost:5432/kotlinfullsample"
        private const val JDBC_DRIVER = "org.postgresql.Driver"
        private const val DB_USER_NAME = "postgres"
        private const val DB_PASSWORD = "postgres"
    }

    val dataSource: DataSource

    init {
        this.dataSource = HikariConfig().let {
            it.jdbcUrl = JDBC_URL
            it.driverClassName = JDBC_DRIVER
            it.username = DB_USER_NAME
            it.password = DB_PASSWORD
            HikariDataSource(it)
        }
    }
}
