package ru.maxultra.meme_creator_telegram_bot.model.network

class NWGenerateMemeRequest(
    val topText: String? = null,
    val bottomText: String? = null,
    val image: ByteArray? = null,
    val imgUrl: String? = null,
)
