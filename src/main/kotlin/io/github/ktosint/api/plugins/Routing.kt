package io.github.ktosint.api.plugins

import com.beust.klaxon.Klaxon
import io.github.ktosint.api.lib.Domain
import io.github.ktosint.api.lib.Email
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

        get("/email/reacher/{email}") {
            val obj = Email(call.parameters["email"]!!)
            val result = obj.Reacher()
            call.respondText(Klaxon().toJsonString(result), ContentType.parse("text/json"))
        }

        get("/email/mboxvalid/{email}/{apikey}") {
            try {
                val obj = Email(call.parameters["email"]!!)
                val result = obj.MBoxValid(call.parameters["apikey"]!!)
                call.respondText(Klaxon().toJsonString(result), ContentType.parse("text/json"))
            } catch (e: IllegalArgumentException) {
                call.respondText("Unable to make request, incorrect key was used.")
            }
        }

        get("/email/haveibeenpwned/{email}") {
            val obj = Email(call.parameters["email"]!!)
            val result = obj.HaveIBeenPwned()
            call.respondText(Klaxon().toJsonString(result), ContentType.parse("text/json"))
        }

        get("/domain/dossier/{domain}") {
            val obj = Domain(call.parameters["domain"]!!)
            val result = obj.Dossier()
            call.respondText(Klaxon().toJsonString(result), ContentType.parse("text/json"))
        }

        get("/domain/redirects/{domain}") {
            val obj = Domain(call.parameters["domain"]!!)
            val result = obj.Redirects()
            call.respondText(Klaxon().toJsonString(result), ContentType.parse("text/json"))
        }

        get("/domain/subdomains/{domain}") {
            val obj = Domain(call.parameters["domain"]!!)
            val result = obj.Subdomains()
            call.respondText(Klaxon().toJsonString(result), ContentType.parse("text/json"))
        }

        get("/domain/similar/{domain}") {
            val obj = Domain(call.parameters["domain"]!!)
            val result = obj.Similar(20000L)
            call.respondText(Klaxon().toJsonString(result.toList()), ContentType.parse("text/json"))
        }
    }
}
