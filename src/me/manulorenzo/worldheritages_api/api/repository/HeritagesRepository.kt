package me.manulorenzo.worldheritages_api.api.repository

import me.manulorenzo.worldheritages_api.api.data.model.Heritage
import me.manulorenzo.worldheritages_api.api.data.model.HeritagesTable
import me.manulorenzo.worldheritages_api.api.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class HeritagesRepository : Repository {
    override suspend fun getHeritagesList(): List<Heritage> = dbQuery {
        HeritagesTable.selectAll().orderBy(HeritagesTable.id to SortOrder.ASC).map { toHeritage(it) }
    }

    override suspend fun insertAll(heritagesJsonList: List<Heritage>) = dbQuery {
        HeritagesTable.batchInsert(
            data = heritagesJsonList,
            body = { heritage: Heritage ->
                this[HeritagesTable.id] = heritage.id
                this[HeritagesTable.year] = heritage.year
                this[HeritagesTable.image] = heritage.image
                this[HeritagesTable.imageAuthor] = heritage.imageAuthor
                this[HeritagesTable.lat] = heritage.lat
                this[HeritagesTable.lng] = heritage.lng
                this[HeritagesTable.name] = heritage.name
                this[HeritagesTable.page] = heritage.page
                this[HeritagesTable.region] = heritage.region
                this[HeritagesTable.regionLong] = heritage.regionLong
                this[HeritagesTable.shortInfo] = heritage.shortInfo
                this[HeritagesTable.longInfo] = heritage.longInfo ?: ""
                this[HeritagesTable.target] = heritage.target
                this[HeritagesTable.type] = heritage.type
            },
            ignore = true
        )
    }

    private fun toHeritage(row: ResultRow): Heritage =
        Heritage(
            id = row[HeritagesTable.id],
            year = row[HeritagesTable.year],
            target = row[HeritagesTable.target],
            name = row[HeritagesTable.name],
            type = row[HeritagesTable.type],
            region = row[HeritagesTable.region],
            lat = row[HeritagesTable.lat],
            lng = row[HeritagesTable.lng],
            page = row[HeritagesTable.page],
            image = row[HeritagesTable.image],
            imageAuthor = row[HeritagesTable.imageAuthor],
            shortInfo = row[HeritagesTable.shortInfo],
            longInfo = row[HeritagesTable.longInfo],
            regionLong = row[HeritagesTable.regionLong]
        )
}