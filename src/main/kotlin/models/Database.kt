package org.example.main.models

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

internal object DbConnection {
    private val hikariConfig by lazy {
        HikariConfig().apply{
            jdbcUrl         = "jdbc:mysql://localhost/shop"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username        = "empty"
            password        = "Emptyy_99"
            maximumPoolSize = 10
            isAutoCommit    = false
        }
    }

    val database by lazy {
        Database.connect(HikariDataSource(hikariConfig))
    }
}