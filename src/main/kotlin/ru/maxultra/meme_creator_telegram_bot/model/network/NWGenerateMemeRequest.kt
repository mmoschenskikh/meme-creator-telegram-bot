package ru.maxultra.meme_creator_telegram_bot.model.network

import kotlinx.serialization.Serializable

@Serializable
@Suppress("UNUSED")
class NWGenerateMemeRequest(
    val topText: String? = null,
    val bottomText: String? = null,
    val imgUrl: String? = null,
)
