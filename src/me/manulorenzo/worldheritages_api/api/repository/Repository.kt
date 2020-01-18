package me.manulorenzo.worldheritages_api.api.repository

import me.manulorenzo.worldheritages_api.api.data.model.Heritage
import org.jetbrains.exposed.sql.ResultRow

interface Repository {
    suspend fun getHeritagesList(): List<Heritage>
    suspend fun insertAll(heritagesJsonList: List<Heritage>): List<ResultRow>
}