package me.manulorenzo.worldheritages_api.api.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object HeritagesTable : Table() {
    val id: Column<String> = varchar("id", length = 20).primaryKey()
    val year: Column<Int> = integer("year")
    val target: Column<String> = varchar("target", length = 4)
    val name: Column<String> = varchar("name", length = 255)
    val type: Column<String> = varchar("type", length = 255)
    val region: Column<String> = varchar("region", length = 255)
    val regionLong: Column<String> = varchar("regionLong", length = 255)
    val lat: Column<Double> = double("lat")
    val lng: Column<Double> = double("lng")
    val page: Column<String> = varchar("page", length = 255)
    val image: Column<String> = varchar("image", length = 255)
    val imageAuthor: Column<String> = text("imageAuthor")
    val shortInfo: Column<String> = text("shortInfo")
    val longInfo: Column<String> = text("longInfo")
}