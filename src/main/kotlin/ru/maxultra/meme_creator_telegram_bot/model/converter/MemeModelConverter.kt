package ru.maxultra.meme_creator_telegram_bot.model.converter

import ru.maxultra.meme_creator_telegram_bot.model.local.MemeModel
import ru.maxultra.meme_creator_telegram_bot.model.network.NWGenerateMemeRequest

object MemeModelConverter {

    fun toNetwork(src: MemeModel) = NWGenerateMemeRequest(
        topText = src.topText,
        bottomText = src.bottomText,
        imgUrl = src.imageUrl,
    )
}
