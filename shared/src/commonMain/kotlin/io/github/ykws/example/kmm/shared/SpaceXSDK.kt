package io.github.ykws.example.kmm.shared

import io.github.ykws.example.kmm.shared.cache.Database
import io.github.ykws.example.kmm.shared.cache.DatabaseDriverFactory
import io.github.ykws.example.kmm.shared.entity.RocketLaunch
import io.github.ykws.example.kmm.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class) suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}
