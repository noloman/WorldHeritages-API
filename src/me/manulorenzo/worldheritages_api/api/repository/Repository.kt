package me.manulorenzo.worldheritages_api.api.repository

import me.manulorenzo.worldheritages_api.api.data.model.Heritage

interface Repository {
    suspend fun getHeritagesList(): List<Heritage>
}