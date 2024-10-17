package io.github.ktosint.api.plugins

import com.beust.klaxon.Klaxon
import io.github.ktosint.api.lib.Username
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/username/{username}") {
            val obj = Username(call.parameters["username"]!!)
            val found: Array<Array<Array<String>>> = obj.SearchSites(false)
            call.respondText(Klaxon().toJsonString(mapOf("found" to found[0], "not-found" to found[1])), ContentType.parse("text/json"))
        }
    }
}
