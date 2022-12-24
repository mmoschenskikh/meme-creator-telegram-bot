package ru.maxultra.meme_creator_telegram_bot.api.config

import ru.maxultra.meme_creator_telegram_bot.utils.getEnvOrThrow

internal val MEME_API_TOKEN: String = getEnvOrThrow("MEME_API_TOKEN")
