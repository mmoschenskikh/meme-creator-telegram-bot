package ru.maxultra.meme_creator_telegram_bot.model.network

import kotlinx.serialization.Serializable

@Serializable
data class NWGenerateMemeResponse(
    val url: String? = null,
    val error: String? = null,
)
