package ru.maxultra.meme_creator_telegram_bot.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeRequest
import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeResponse

class MemeApi(
    private val client: HttpClient,
) : IMemeApi {

    override suspend fun generateMeme(request: NWGenerateMemeRequest): NWGenerateMemeResponse =
        client.submitFormWithBinaryData(
            url = "generateMeme",
            formData = formData {
                request.topText?.let { append("topText", it) }
                request.bottomText?.let { append("bottomText", it) }
                request.image?.let { append("image", it) }
                request.imgUrl?.let { append("imgUrl", it) }
            }
        ).body()
}
