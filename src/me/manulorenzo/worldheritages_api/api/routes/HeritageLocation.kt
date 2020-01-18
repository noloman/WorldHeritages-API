package me.manulorenzo.worldheritages_api.api.routes

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import me.manulorenzo.worldheritages_api.api.data.model.Heritage
import me.manulorenzo.worldheritages_api.api.repository.Repository

const val HERITAGES = "/heritages"

@KtorExperimentalLocationsAPI
@Location(HERITAGES)
class HeritagesLocation

@KtorExperimentalLocationsAPI
fun Route.heritages(db: Repository) {
    get<HeritagesLocation> {
        val heritagesList: List<Heritage> = db.getHeritagesList()
        call.respond(heritagesList)
    }
}