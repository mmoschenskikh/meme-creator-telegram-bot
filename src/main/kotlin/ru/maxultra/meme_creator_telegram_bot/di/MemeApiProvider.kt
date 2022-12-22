package ru.maxultra.meme_creator_telegram_bot.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import ru.maxultra.meme_creator_telegram_bot.api.MemeApi
import ru.maxultra.meme_creator_telegram_bot.api.MemeApiConst
import ru.maxultra.meme_creator_telegram_bot.api.config.ServerConfig

class MemeApiProvider(private val serverConfig: ServerConfig) {

    private val httpClient by lazy {
        HttpClient(CIO) {
            install(DefaultRequest)
            install(ContentNegotiation) {
                json()
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = serverConfig.host
                    serverConfig.basePath?.let { path(it) }
                }
                header(MemeApiConst.TOKEN_HEADER, serverConfig.token)
            }
        }
    }

    val memeApi by lazy { MemeApi(httpClient) }
}
