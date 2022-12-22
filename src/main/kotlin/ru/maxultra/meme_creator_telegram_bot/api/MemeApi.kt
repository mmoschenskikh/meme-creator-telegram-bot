package ru.maxultra.meme_creator_telegram_bot.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeRequest
import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeResponse

class MemeApi(
    private val client: HttpClient,
) : IMemeApi {

    override suspend fun generateMeme(request: NWGenerateMemeRequest): NWGenerateMemeResponse =
        client.post("generateMeme") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
