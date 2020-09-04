package io.github.ykws.example.kmm.shared.cache

import io.github.ykws.example.kmm.shared.entity.RocketLaunch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllRockets()
            dbQuery.removeAllLaunches()
        }
    }

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).excuteAsList()
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName
        )
    }
}
