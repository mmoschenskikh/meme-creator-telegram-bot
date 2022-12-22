package ru.maxultra.meme_creator_telegram_bot.data

import ru.maxultra.meme_creator_telegram_bot.api.MemeApi
import ru.maxultra.meme_creator_telegram_bot.model.converter.MemeModelConverter
import ru.maxultra.meme_creator_telegram_bot.model.local.MemeModel
import ru.maxultra.meme_creator_telegram_bot.utils.nullIfBlank

class MemeGeneratorRepository(
    private val memeApi: MemeApi,
) {

    suspend fun generateMeme(meme: MemeModel): String? {
        val request = MemeModelConverter.toNetwork(meme)
        val response = memeApi.generateMeme(request)
        return response.url?.nullIfBlank()
    }
}
