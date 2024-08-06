package com.nmi.lexiloop.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

class DesktopDatabaseDriverFactory() : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return JdbcSqliteDriver(url = JdbcSqliteDriver.IN_MEMORY, schema = QuizDatabase.Schema)
    }
}