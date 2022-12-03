package ru.maxultra.meme_creator_telegram_bot.api

import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeRequest
import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeResponse

interface IMemeApi {
    suspend fun generateMeme(request: NWGenerateMemeRequest): NWGenerateMemeResponse
}
