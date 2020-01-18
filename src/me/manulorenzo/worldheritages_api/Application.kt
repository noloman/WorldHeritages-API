package me.manulorenzo.worldheritages_api

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DataConversion
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.routing.routing
import io.ktor.server.jetty.EngineMain
import kotlinx.coroutines.launch
import me.manulorenzo.worldheritages_api.api.data.ParsingManager
import me.manulorenzo.worldheritages_api.api.data.model.Heritage
import me.manulorenzo.worldheritages_api.api.repository.DatabaseFactory
import me.manulorenzo.worldheritages_api.api.repository.HeritagesRepository
import me.manulorenzo.worldheritages_api.api.routes.heritages
import org.slf4j.event.Level
import java.text.DateFormat

fun main(args: Array<String>): Unit = EngineMain.main(args)

@KtorExperimentalLocationsAPI
fun Application.module() {
    install(Compression)
    install(Locations) {
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    install(DataConversion)
    install(DefaultHeaders)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    DatabaseFactory.init()
    val db = HeritagesRepository()

    launch {
        val heritagesJsonList: List<Heritage> = ParsingManager.parse()
        db.insertAll(heritagesJsonList)
    }

    routing {
        heritages(db)
    }
}

